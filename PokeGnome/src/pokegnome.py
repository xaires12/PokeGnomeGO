import os
import sqlite3

from flask import Flask, render_template, request

import db

app = Flask(__name__)


@app.route('/')
def home():
    return render_template('home.html')


# @app.route('/enternew')
# def new_student():
#     return render_template('hello_30_student.html')


@app.route('/gnomes')
def list_gnomes():
    con = sqlite3.connect(database)
    con.row_factory = sqlite3.Row
    
    cur = con.cursor()
    cur.execute("select * from gnome")
    
    rows = cur.fetchall();
    for row in rows:
        db.write_file(row[4], f'photo/{row[1]}.jpg')

    return render_template("gnomes.html",rows = rows)


if __name__ == '__main__':
    database = '../instance/pokegnome.sqlite3'
    app.run(debug = True)
