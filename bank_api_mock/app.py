from flask import Flask
app = Flask(__name__)
from flask import jsonify

user_req_counter = {}

@app.route('/ok/<username>')
def ok(username):
    global user_req_counter
    if username in user_req_counter:
        user_req_counter[username] += 1
    else:
        user_req_counter[username] = 1
    
    
    if user_req_counter[username] > 4:
        return jsonify({"approved": True})
    return jsonify({"approved": False})
    