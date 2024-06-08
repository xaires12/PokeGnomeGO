#! python3

import base64
import getpass
import logging
import random
import sqlite3


def init_db(database):
    """
    Initialise the database.

    :param database: path to SQLite database
    """

    with open('schema.sql') as sql_file:
        sql = sql_file.read()
        # read SQL for database creation

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.executescript(sql)
        # run the SQL over the database
        conn.commit()
        cursor.close()

    return


def create_user(database, login, password):
    """
    Add the user to the database.

    :param database: path to SQLite database
    :type database: string

    :param login: user login
    :type database: string

    :param password: user password
    :type database: string
    """

    sql = '''
    INSERT INTO user (login, password) VALUES (?, ?)
    '''

    if not user:
        user = input('Podaj nazwę użytkownika: ')
        # ask for user name if not given

    if not password:
        password = getpass.getpass('Podaj hasło użytkownika: ')
        # ask for password if not given

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.execute(sql, (login, password))
        # run the SQL over the database
        conn.commit()
        cursor.close()

    # TODO: if user exists, ask if password change

    return


def create_gnome(database, name, longitude, latitude, photo_path=None):
    """
    Add the gnome to the database.

    :param database: path to SQLite database
    :type database: string

    :param name: gnome name
    :type name: string

    :param longitude: gnome coordinates (longitude)
    :type longitude: float

    :param latitude: gnome coordinates (latitude)
    :type latitude: float
    """

    sql = '''
    INSERT INTO gnome (name, longitude, latitude, photo)
    VALUES (?, ?, ?, ?)
    '''

    photo = read_file(photo_path)
    # prepare file

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.execute(sql,
            (name, longitude, latitude, sqlite3.Binary(photo))
        )
        # run the SQL over the database
        conn.commit()
        cursor.close()

    return


def update_gnome(database, gnome_id, name=None, longitude=None, latitude=None, photo_path=None):
    """
    Add the gnome to the database.

    :param database: path to SQLite database
    :type database: string

    :param name: gnome name
    :type name: string

    :param longitude: gnome coordinates (longitude)
    :type longitude: float

    :param latitude: gnome coordinates (latitude)
    :type latitude: float
    """

    sql = '''
    UPDATE gnome
    SET name = ?,
        longitude = ?,
        latitude = ?,
        photo = ?
    WHERE gnome_id = ?
    '''

    photo = read_file(photo_path)
    # prepare file

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.execute(sql,
            (name, longitude, latitude, sqlite3.Binary(photo), gnome_id)
        )
        # run the SQL over the database
        conn.commit()
        cursor.close()

    return


def read_gnome(database, gnome_id, path=''):
    """
    Read the gnome from the database and write the photo to the active directory.

    :param database: path to SQLite database
    :type database: string

    :param gnome_id: gnome identifier
    :type gnome_id: integer

    :param path: path to write the gnome photo
        If None, photo is not written to file.
    :type path: string

    :returns: name, longitude, latitude, photo_path
    :rtype: tuple (string, float, float, string)
    """

    sql = '''
    SELECT name, longitude, latitude, photo
    FROM gnome
    WHERE gnome_id = ?
    '''

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.execute(sql, (gnome_id,))
        # run the SQL over the database
        try:
            name, longitude, latitude, photo = cursor.fetchone()
        except TypeError:
            name = longitude = latitude = photo = None
        finally:
            cursor.close()

    if (path is None) or (photo is None):
        photo_path = None
    else:
        # prepare path for the photo file
        if path and path.endswith('/'):
            photo_path = f'{path}{name}.jpg'
        elif path:
            photo_path = f'{path}/{name}.jpg'
        else:
            # no path given
            photo_path = f'{name}.jpg'

        write_file(photo, photo_path)

    return (name, longitude, latitude, photo_path)


def draw_gnome(database, user):
    """
    For each user in the database who didn't found all user, draw a gnome.

    :param database: path to SQLite database
    :type database: string

    :param user: user id for which draw the gnome
    :type user: integer
    """

    sql_users = """
        SELECT
            user.user_id
        FROM
            user
            LEFT JOIN (
                SELECT
                    user_id
                FROM
                    visit
                WHERE
                    visit.visit_date IS NOT NULL
                ) visit_no_date
                ON (user.user_id = visit_no_date.user_id)
        WHERE
            1 = 1
        GROUP BY
            user.user_id
        HAVING
            COUNT(user.user_id) < 
                (
                SELECT
                    COUNT(*) AS ile
                FROM
                    gnome
                )
    """
    # ids of users who didn't find all the gnomes

    sql_gnomes = """
        SELECT
            gnome.gnome_id
        FROM
            gnome
            LEFT JOIN (
                SELECT
                    gnome_id
                FROM
                    visit
                WHERE
                    1 = 1
                    AND visit_date IS NOT NULL
                    AND user_id = ?  -- dla użytkownika
                ) visit_no_date
                ON (gnome.gnome_id = visit_no_date.gnome_id)
        WHERE
            1 = 1
            AND visit_no_date.gnome_id IS NULL
    """
    # ids of gnomes not found by the given user

    with sqlite3.connect(database) as conn:
        cursor = conn.cursor()
        cursor.execute(sql_users)
        users = cursor.fetchall()

        for user in users:
            cursor.execute(sql_gnomes, (user[0], ))
            # run the SQL over the database
            gnomes = [id[0] for id in cursor.fetchall()]
            drawn_gnome = random.choice(gnomes)

            print(f'user: {user[0]} - gnomes: {gnomes} -> {drawn_gnome}')

        cursor.close()

    return


def read_file(file_path):
    """
    Prepare the file to insert into the SQLite database.

    :param file_path: path to file
    :type file_path: string

    :returns: representation of the binary file
    :rtype: binary data
    """

    try:
        with open(file_path, 'rb') as f:
            ablob = base64.b64encode(f.read())
    except Exception as err:
        logging.error(f'Błąd odczytu pliku: {err}.')
        raise

    return ablob


def write_file(ablob, file_path):
    """
    Write the given BLOB as file.

    :param file_path: path to file
    :type file_path: string

    :param ablob: representation of the binary file
    :type ablob: binary data
    """

    if ablob:
        try:
            with open(file_path, 'wb') as f:
                f.write(base64.b64decode(ablob))
            return True
        except Exception as err:
            logging.error(f'Błąd zapisu pliku: {err}.')
            return False

    return


if __name__ == '__main__':
    sqlite_database = '../instance/pokegnome.sqlite3'

    # init_db(sqlite_database)
    # # initialize database

    # try:
    #     user = 'test_user1'
    #     password = 'password1'
    #     create_user(sqlite_database, user, password)
    # except Exception as err:
    #     logging.error(f'Błąd dopisania użytkownika do bazy: {err}.')

    # file_path = r'/home/seba/Obrazy/Agorek_(Agora)_Wroclaw_dwarf.JPG'
    # ablob = read_file(file_path)
    # write_file(ablob, 'picture.jpg')

    try:
        create_gnome(sqlite_database,
                'Agorek', 17.021536, 51.134747,
                r'/home/seba/Obrazy/Agorek_(Agora)_Wroclaw_dwarf.JPG')
    except Exception as err:
        logging.error(f'Błąd dopisania krasnoludka do bazy: {err}.')

    update_gnome(sqlite_database, 1,
            'Automatek', 17.06528, 51.10861,
            r'/home/seba/Obrazy/Automatek_(Machine-man)_Wroclaw_dwarf_01.JPG')
    update_gnome(sqlite_database, 2,
            '100matolog', 17.058714, 51.095006,
            r'/home/seba/Obrazy/100matolog_(Tooth-dwarf)_Wroclaw_dwarf_01.JPG')
    update_gnome(sqlite_database, 3,
            'Adwokatka', 17.041889, 51.118389,
            r'/home/seba/Obrazy/Adwokatka_(Advocate)_Wroclaw_dwarf_01.jpg')
    # print(read_gnome(sqlite_database, 5))

    # draw_gnome(sqlite_database, 2)
    # # draw gnomes for the users
