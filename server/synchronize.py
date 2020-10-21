#!/usr/bin/env python3
# coding: utf-8

import os
import re
import sys
import json
import time
import urllib
import base64
import hashlib
import pymysql
import requests

userConfig = {
    'user': {},
    'database': {}
}

createTableStatement = [
    {'name': 'user'             ,'statement': 'CREATE TABLE user (code CHAR(10) NOT NULL, passwordHash CHAR(40) NOT NULL, realName CHAR(20) NULL, gender CHAR(2) NULL, className CHAR(50) NULL, majorName CHAR(50) NULL, departmentName CHAR(50) NULL, email CHAR(255) NULL, homeAddress CHAR(255) NULL, mobilePhone CHAR(20) NULL, PRIMARY KEY (code)) ENGINE = InnoDB'},
    {'name': 'class'            ,'statement': 'CREATE TABLE class (id INT NOT NULL, code CHAR(20) NULL, className CHAR(50) NOT NULL, grade CHAR(5) NULL, studentCount INT NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'department'       ,'statement': 'CREATE TABLE department (id INT NOT NULL, code CHAR(5) NULL, departmentName CHAR(50) NOT NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'room'             ,'statement': 'CREATE TABLE room (id INT NOT NULL, code CHAR(20) NULL, roomName CHAR(20) NOT NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'lessonType'       ,'statement': 'CREATE TABLE lessonType (id INT NOT NULL, code CHAR(5) NULL, lessonTypeName CHAR(20) NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'examMode'         ,'statement': 'CREATE TABLE examMode (id INT NOT NULL, code CHAR(5) NULL, examModeName char(50) NOT NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'lesson'           ,'statement': 'CREATE TABLE lesson (id INT NOT NULL, classCode CHAR(15) NOT NULL, code CHAR(15) NOT NULL, typeId INT NOT NULL, flag CHAR(20) NULL, nameZh CHAR(255) NOT NULL, nameEn CHAR(255) NULL, departmentId INT NOT NULL, periodTotal INT NULL, studentCount INT NULL,scheduleText CHAR(255) NULL, examModeId INT NOT NULL, PRIMARY KEY(id), CONSTRAINT fk_lesson_lessonType FOREIGN KEY (typeId) REFERENCES lessonType (id), CONSTRAINT fk_lesson_department FOREIGN KEY (departmentId) REFERENCES department (id), CONSTRAINT fk_lesson_examMode FOREIGN KEY (examModeId) REFERENCES examMode (id)) ENGINE = InnoDB'},
    {'name': 'teacher'          ,'statement': 'CREATE TABLE teacher (id INT NOT NULL, code CHAR(10) NULL, hireType CHAR(20) NULL, teacherName CHAR(20) NOT NULL, age INT NULL, departmentId INT NULL, departmentName CHAR(50) NULL, title CHAR(50) NULL, PRIMARY KEY (id)) ENGINE = InnoDB'},
    {'name': 'classmate'        ,'statement': 'CREATE TABLE classmate (code CHAR(10) NOT NULL, classmateName CHAR(20) NOT NULL, gender CHAR(2) NULL, classId INT NOT NULL, className CHAR(50) NULL, majorName CHAR(50) NOT NULL, departmentId INT NOT NULL, departmentName CHAR(50) NOT NULL, PRIMARY KEY (code)) ENGINE = InnoDB'},
    {'name': 'lessonClasses'    ,'statement': 'CREATE TABLE lessonClasses (classId INT NOT NULL, lessonId INT NOT NULL, PRIMARY KEY (classId, lessonId), CONSTRAINT fk_lessonClasses_class FOREIGN KEY (classId) REFERENCES class (id), CONSTRAINT fk_lessonClasses_lesson FOREIGN KEY (lessonId) REFERENCES lesson (id)) ENGINE = InnoDB'},
    {'name': 'lessonTeachers'   ,'statement': 'CREATE TABLE lessonTeachers (lessonId INT NOT NULL, teacherId INT NOT NULL, teacherRole CHAR(20) NULL, indexNo INT NULL, PRIMARY KEY (lessonId, teacherId), CONSTRAINT fk_lessonTeachers_lesson FOREIGN KEY (lessonId) REFERENCES lesson (id), CONSTRAINT fk_lessonTeachers_teacher FOREIGN KEY (teacherId) REFERENCES teacher (id)) ENGINE = InnoDB'},
    {'name': 'lessonClassmates' ,'statement': 'CREATE TABLE lessonClassmates(classmateCode CHAR(10) NOT NULL, lessonId INT NOT NULL, PRIMARY KEY (classmateCode, lessonId), CONSTRAINT fk_lessonClassmates_classmate FOREIGN KEY (classmateCode) REFERENCES classmate (code), CONSTRAINT fk_lessonClassmates_lesson FOREIGN KEY (lessonId) REFERENCES lesson (id)) ENGINE = InnoDB'},
    {'name': 'schedule'         ,'statement': 'CREATE TABLE schedule (lessonId INT NOT NULL, periods INT NULL, startDate DATE NOT NULL, startTime TIME NOT NULL, endTime TIME NOT NULL, startWeekday INT NOT NULL, weekIndex INT NOT NULL, roomId INT NOT NULL, PRIMARY KEY (lessonId, startDate, startTime), CONSTRAINT fk_schedule_lesson FOREIGN KEY (lessonId) REFERENCES lesson (id), CONSTRAINT fk_schedule_room FOREIGN KEY (roomId) REFERENCES room (id)) ENGINE = InnoDB'}
]

mobileFakeHeader = {
    'User-Agent': 'Mozilla/5.0 (Linux; Android 9; MI 6X Build/PKQ1.180904.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/85.0.4183.127 Mobile Safari/537.36',
    'Content-Type': 'application/x-www-form-urlencoded'
}

fakeHeader = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0'
}

configFileParentDir = os.path.join(os.path.expanduser('~'), '.classSchedule')
configFileDir = os.path.join(configFileParentDir, 'config.json')

def sha1Hash(text):
    if(type(text) != str):
        return None
    sha1HashObj = hashlib.sha1()
    sha1HashObj.update(text.encode('utf-8'))
    return sha1HashObj.hexdigest()

if(os.path.exists(configFileDir)):
    print(f"using config file '{configFileDir}'");
    configFile = open(configFileDir, 'r')
    configJson = json.load(configFile)
    configFile.close()
    userConfig['user']['username'] = configJson.get('user', {}).get('username')
    userConfig['user']['password'] = configJson.get('user', {}).get('password')
    if(None in userConfig['user'].values()):
        print(f"invalid config file at '{configFileDir}'", file=sys.stderr)
        sys.exit()
    userConfig['database']['host'] = configJson.get('database', {}).get('host', '127.0.0.1')
    userConfig['database']['port'] = configJson.get('database', {}).get('port', '3306')
    userConfig['database']['port'] = int(userConfig['database']['port'])
    userConfig['database']['user'] = configJson.get('database', {}).get('user', 'root')
    userConfig['database']['password'] = configJson.get('database', {}).get('password', 'root')
    userConfig['database']['database'] = configJson.get('database', {}).get('database', 'classSchedule')
else:
    print(f"creating config file '{configFileDir}'")
    if(not os.path.exists(configFileParentDir)):
        os.mkdir(configFileParentDir, mode=0o700)
    userConfig['user']['username'] = input('username for school account: ')
    userConfig['user']['password'] = input('password for school account: ')
    userConfig['database']['host'] = (input('hostname for database (default: 127.0.0.1): ') or '127.0.0.1')
    userConfig['database']['port'] = (input('port number for database (default: 3306): ') or '3306')
    userConfig['database']['port'] = int(userConfig['database']['port'])
    userConfig['database']['user'] = (input('username for database (default: root): ') or 'root')
    userConfig['database']['password'] = (input('password for database (default: root): ') or 'root')
    userConfig['database']['database'] = (input('database name for database (default: classSchedule): ') or 'classSchedule')
    configFile = open(configFileDir, 'x')
    json.dump(userConfig, configFile, ensure_ascii=False, indent=4)
    configFile.close()
    os.chmod(configFileDir, 0o600)

print('Synchronize data with config:')
for i, j in userConfig.items():
    for ii, iii in j.items():
        print(f"    {'.'.join([i, ii]):18}: {iii}")

print('processing session ... ', end='')
sessionPageUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/home'
sessionResponse = requests.get(url = sessionPageUrl, headers = fakeHeader)
userSession = sessionResponse.request.headers.get('Cookie')
if(userSession == None):
    print('failed to get session', file=sys.stderr)
    sys.exit()
fakeHeader['Cookie'] = userSession
print(userSession)

print('processing salt ... ', end='')
saltPageUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/login-salt'
saltResponse = requests.get(url = saltPageUrl, headers = fakeHeader)
saltResponse.raise_for_status()
userSalt = saltResponse.text
print(userSalt)

print('logging in ... ', end='')
loginPostUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/login'
loginData = {
    'username': userConfig['user']['username'],
    'password': sha1Hash(userSalt + '-' + userConfig['user']['password']),
    'captcha': ''
}
loginResponse = requests.post(url = loginPostUrl, headers = fakeHeader, json = loginData)
loginResponse.raise_for_status()
loginResultJson = json.loads(loginResponse.text)
if(loginResultJson['result'] == False):
    print(f"login failed: {loginResultJson['message']}", file=sys.stderr)
    sys.exit()
print('done')

print('processing dataId ... ', end='')
dataIdPageUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/for-std/course-table'
dataIdResponse = requests.get(url = dataIdPageUrl, headers = fakeHeader)
dataIdResponse.raise_for_status()
userDataId = re.search(r'\d+$', dataIdResponse.request.url).group()
print(userDataId)
print('processing bizTypeId and semesterId ... ', end='')
bizTypeAndSemesterIdPageUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/for-std/course-table/info/' + userDataId
bizTypeAndSemesterIdResponse = requests.get(url = bizTypeAndSemesterIdPageUrl, headers = fakeHeader)
bizTypeAndSemesterIdResponse.raise_for_status()
bizTypeAndSemesterIdResponse.encoding = 'utf-8'
userBizTypeId = re.search(r'(?i)(?:biztypeid:\s?)(?P<bizTypeId>\d+)(?:,)', bizTypeAndSemesterIdResponse.text).group('bizTypeId')
print(f'bizTypeId = {userBizTypeId}', end='')
userSemesterIdDict = re.search(r'(?:<option selected="selected" value=\")(?P<semesterId>\d+)(?:\">)(?P<semesterName>.*)(?:</option>)', bizTypeAndSemesterIdResponse.text).groupdict()
print(f", semester: {userSemesterIdDict['semesterName']} id = {userSemesterIdDict['semesterId']}")
userIdsDict = {
    'bizTypeId': userBizTypeId,
    'semesterId': userSemesterIdDict['semesterId'],
    'dataId': userDataId
}

print('processing course data ... ', end='')
courseDataIndexUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/for-std/course-table/get-data'
courseDataResponse = requests.get(url=courseDataIndexUrl, params=userIdsDict, headers=fakeHeader)
courseDataResponse.raise_for_status()
courseDataJson = json.loads(courseDataResponse.text)
print('got courses:')
for i in courseDataJson['lessonIds']:
    print(f'    id: {i}')

print('processing schedule data ... ', end='')
scheduleDataUrl = 'http://jxglstu.hfut.edu.cn/eams5-student/ws/schedule-table/datum'
scheduleDataPostData = {
    'lessonIds': courseDataJson['lessonIds'],
    'weekIndex': '',
    'studentId': int(userIdsDict['dataId'])
}
scheduleDataResponse = requests.post(url = scheduleDataUrl, headers = fakeHeader, json = scheduleDataPostData)
scheduleDataResponse.raise_for_status()
scheduleDataJson = json.loads(scheduleDataResponse.text)['result']
print(f"got {len(scheduleDataJson['scheduleList'])} schedules")

print('logging in to app ... ', end='')
mobileLoginPostUrl = 'http://jxglstu.hfut.edu.cn:7070/appservice/home/appLogin/login.action'
mobileLoginDataDict = {
    'password': base64.b64encode(userConfig['user']['password'].encode('utf-8')).decode('utf-8'),
    'username': userConfig['user']['username'],
    'identity': '0'
}
mobileLoginData = urllib.parse.urlencode(mobileLoginDataDict)
mobileLoginResponse = requests.post(url=mobileLoginPostUrl, headers=mobileFakeHeader, data=mobileLoginData)
mobileLoginResponse.raise_for_status()
mobileLoginResponseJson = json.loads(mobileLoginResponse.text)
mobileUserKey = mobileLoginResponseJson['obj']['userKey']
print('done, got user data:')
for i, j in mobileLoginResponseJson["obj"]["business_data"].items():
    print(f"    {i:<16}: {j or 'null'}")

print('processing mobile semester code ... ', end='')
mobileSemesterDataUrl = 'http://jxglstu.hfut.edu.cn:7070/appservice/home/publicdata/getSemesterAndWeekList.action'
mobileSemesterDataPostData = {
    'projectId': '2',
    'userKey': mobileUserKey
}
mobileSemesterDataResponse = requests.post(url = mobileSemesterDataUrl, headers = mobileFakeHeader, data = urllib.parse.urlencode(mobileSemesterDataPostData))
mobileSemesterDataResponse.raise_for_status()
mobileSemesterDataResponseJson = json.loads(mobileSemesterDataResponse.text)
mobileSemesterCode = mobileSemesterDataResponseJson['obj']['business_data']['cur_semester_code']
print(mobileSemesterCode)

print('processing classmate data ...')
classmateDataUrl = 'http://jxglstu.hfut.edu.cn:7070/appservice/home/schedule/getClassList.action'
classmateDataPostData = {
    'lessonCode': '',
    'projectId': '2',
    'semestercode': mobileSemesterCode,
    'userKey': mobileUserKey
}
classmateList = []
for i in courseDataJson['lessons']:
    print(f"processing course {i['course']['nameZh']} ... ", end='')
    classmateDataPostData['lessonCode'] = i['code']
    classmateDataResponse = requests.post(url=classmateDataUrl, headers=mobileFakeHeader, data=urllib.parse.urlencode(classmateDataPostData))
    classmateDataResponse.raise_for_status()
    classmateDataResponseJson = json.loads(classmateDataResponse.text)
    classmateList.append({
        'lessonCode': i['code'],
        'classmateList': classmateDataResponseJson['obj']['business_data']
    })
    print(f"got {len(classmateDataResponseJson['obj']['business_data'])} classmates")

print(f"connecting to database {userConfig['database']['host']}:{userConfig['database']['port']} ... ", end='')
connection = pymysql.connect(
    cursorclass = pymysql.cursors.Cursor,
    host        = userConfig['database']['host'],
    port        = userConfig['database']['port'],
    user        = userConfig['database']['user'],
    password    = userConfig['database']['password'],
    charset     = 'utf8mb4'
)
connection.autocommit(True)
print('connected')
databaseCursor = connection.cursor()
databaseChanged = False

databaseCursor.execute('SHOW DATABASES')
if(userConfig['database']['database'] not in [i[0] for i in databaseCursor.fetchall()]):
    print(f"missing database: database {userConfig['database']['database']}, creating ... ", end='')
    databaseCursor.execute(f"CREATE DATABASE {userConfig['database']['database']}")
    print('done')
connection.select_db(userConfig['database']['database'])
databaseCursor.execute('SHOW TABLES')
existingTables = [i[0] for i in databaseCursor.fetchall()];
for createTable in createTableStatement:
    if(createTable['name'] not in existingTables):
        print(f"missing table: {createTable['name']}, creating ... ", end='')
        databaseCursor.execute(createTable['statement'])
        print('done')

userInfoLocal = (
    userConfig['user']['username'],
    sha1Hash(userConfig['user']['password']),
    mobileLoginResponseJson['obj']['business_data']['user_name'],
    mobileLoginResponseJson['obj']['business_data']['gender'],
    mobileLoginResponseJson['obj']['business_data']['adminclass_name'],
    mobileLoginResponseJson['obj']['business_data']['major_name'],
    mobileLoginResponseJson['obj']['business_data']['depart_name'],
    mobileLoginResponseJson['obj']['business_data']['account_email'],
    mobileLoginResponseJson['obj']['business_data']['ancestral_addr'],
    mobileLoginResponseJson['obj']['business_data']['mobile_phone']
)
databaseCursor.execute(f"DELETE FROM user WHERE code != '{userInfoLocal[0]}'")
if(databaseCursor.execute(f"SELECT * FROM user WHERE code = '{userInfoLocal[0]}'")):
    userInfoRemote = databaseCursor.fetchone()
    if(userInfoRemote != userInfoLocal):
        print('updating user info ... ', end='')
        databaseCurson.execute('UPDATE user SET ' + ', '.join([f'{i[0]} = %s' for i in databaseCursor.description[1:]]) + f' WHERE code = {userInfoLocal[0]}', userInfoLocal[1:])
        print('done')
else:
    print('inserting user info ... ', end='')
    databaseCursor.execute('INSERT INTO user VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', userInfoLocal)
    print('done')

print('sort out info list')
infoList = []

tempIdList = []
tempInfoList = []
for i in scheduleDataJson['lessonList']:
    for ii in i['adminclasses']:
        if(ii['id'] not in tempIdList):
            tempInfoList.append((
                ii['id'],
                ii['code'],
                ii['nameZh'],
                ii['grade'],
                ii['stdCount']
            ))
            tempIdList.append(ii['id'])
infoList.append(('class', 2, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    ii = i['openDepartment']
    if(ii['id'] not in tempIdList):
        tempInfoList.append((
            ii['id'],
            ii['code'],
            ii['nameZh']
        ))
        tempIdList.append(ii['id'])
infoList.append(('department', 2, tempInfoList))

tempIdList = []
tempInfoList = []
for i in scheduleDataJson['scheduleList']:
    ii = i['room']
    if(ii['id'] not in tempIdList):
        tempInfoList.append((
            ii['id'],
            ii['code'],
            ii['nameZh']
        ))
        tempIdList.append(ii['id'])
infoList.append(('room', 2, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    ii = i['courseType']
    if(ii['id'] not in tempIdList):
        tempInfoList.append((
            ii['id'],
            ii['code'],
            ii['nameZh']
        ))
        tempIdList.append(ii['id'])
infoList.append(('lessonType', 2, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    ii = i['examMode']
    if(ii['id'] not in tempIdList):
        tempInfoList.append((
            ii['id'],
            ii['code'],
            ii['nameZh']
        ))
        tempIdList.append(ii['id'])
infoList.append(('examMode', 2, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    if(i['id'] not in tempIdList):
        tempInfoList.append((
            i['id'],
            i['code'],
            i['course']['code'],
            i['courseType']['id'],
            courseDataJson['lessonId2Flag'][str(i['id'])],
            i['course']['nameZh'],
            i['course']['nameEn'],
            i['openDepartment']['id'],
            i['course']['periodInfo']['total'],
            i['stdCount'],
            i['scheduleText']['dateTimePlacePersonText']['textZh'],
            i['examMode']['id']
        ))
        tempIdList.append(i['id'])
infoList.append(('lesson', 5, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    for ii in i['teacherAssignmentList']:
        if(ii['teacher']['id'] not in tempIdList):
            tempInfoList.append((
                ii['teacher']['id'],
                ii['teacher']['code'],
                ii['teacher']['hireType'],
                ii['teacher']['person']['nameZh'],
                ii['age'],
                ii['teacher']['department']['id'],
                ii['teacher']['department']['nameZh'],
                ii['teacher']['title']['nameZh'] if (ii['teacher']['title'] != None) else None
            ))
            tempIdList.append(ii['teacher']['id'])
infoList.append(('teacher', 3, tempInfoList))

tempIdList = []
tempInfoList = []
for i in classmateList:
    for ii in i['classmateList']:
        if(ii['code'] not in tempIdList):
            classId = 0
            for iii in infoList[0][2]:
                if(iii[2] == ii['adminclass_name']):
                    classId = iii[0]
                    break
            departmentId = 0
            for iii in infoList[1][2]:
                if(iii[2] == ii['depart_name']):
                    departmentId = iii[0]
                    break
            tempInfoList.append((
                ii['code'],
                ii['name'],
                ii['gender'],
                classId,
                ii['adminclass_name'],
                ii['major_name'],
                departmentId,
                ii['depart_name']
            ))
            tempIdList.append(ii['code'])
infoList.append(('classmate', 1, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    for ii in i['adminclassIds']:
        if((ii, i['id']) not in tempIdList):
            tempInfoList.append((ii, i['id']))
            tempIdList.append((ii, i['id']))
infoList.append(('lessonClasses', 0, tempInfoList))

tempIdList = []
tempInfoList = []
for i in courseDataJson['lessons']:
    for ii in i['teacherAssignmentList']:
        if((i['id'], ii['teacher']['id']) not in tempIdList):
            tempInfoList.append((
                i['id'],
                ii['teacher']['id'],
                ii['role'],
                ii['indexNo']
            ))
            tempIdList.append((i['id'], ii['teacher']['id']))
infoList.append(('lessonTeachers', 0, tempInfoList))

tempIdList = []
tempInfoList = []
for i in classmateList:
    for ii in i['classmateList']:
        lessonId = 0
        for iii in infoList[5][2]:
            if i['lessonCode'] == iii[1]:
                lessonId = iii[0]
                break
        if((ii['code'], i['lessonCode']) not in tempIdList):
            tempInfoList.append((ii['code'], lessonId))
            tempIdList.append((ii['code'], lessonId))
infoList.append(('lessonClassmates', 0, tempInfoList))

tempIdList = []
tempInfoList = []
for i in scheduleDataJson['scheduleList']:
    if((i['lessonId'], i['date'], i['startTime']) not in tempIdList):
        tempInfoList.append((
            i['lessonId'],
            i['periods'],
            i['date'],
            i['startTime'] * 100,
            i['endTime'] * 100,
            i['weekday'],
            i['weekIndex'],
            i['room']['id']
        ))
        tempIdList.append((i['lessonId'], i['date'], i['startTime']))
infoList.append(('schedule', 0, tempInfoList))

print('done')

for infoName, nameColumnIndex, info in infoList[:7]:
    for infoLocal in info:
        if(databaseCursor.execute(f'SELECT * FROM {infoName} WHERE id = {infoLocal[0]}')):
            infoRemote = databaseCursor.fetchone()
            if(infoRemote != infoLocal):
                print(f'updating {infoName} info {infoLocal[nameColumnIndex]} ... ', end='')
                databaseCursor.execute(f'UPDATE {infoName} SET ' + ', '.join([f'{i[0]} = %s' for i in databaseCursor.description[1:]]) + f' WHERE id = {infoLocal[0]}', infoLocal[1:])
                print('done')
        else:
            print(f'inserting {infoName} info {infoLocal[nameColumnIndex]} ... ', end='')
            databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
            print('done')

for infoName, nameColumnIndex, info in infoList[:7]:
    databaseCursor.execute(f'SELECT id FROM {infoName}')
    infoRemote = databaseCursor.fetchall()
    idListRemote = [i[0] for i in infoRemote]
    idListLocal = [i[0] for i in info]
    outdatedIdList = [i for i in idListRemote if i not in idListLocal]
    if(not outdatedIdList):
        for i in outdatedIdList:
            print(f'outdated date in {infoName}, id = {i}, deleting ... ', end='')
            databaseCursor.execute(f'DELETE FROM {infoName} WHERE id = {i}')
            print('done')

infoName, nameColumnIndex, info = infoList[7]
for infoLocal in info:
    if(databaseCursor.execute(f"SELECT * FROM {infoName} WHERE code = '{infoLocal[0]}'")):
        infoRemote = databaseCursor.fetchone()
        if(infoRemote != infoLocal):
            print(f'updating {infoName} info {infoLocal[nameColumnIndex]} ... ', end='')
            databaseCursor.execute(f'UPDATE {infoName} SET ' + ', '.join([f'{i[0]} = %s' for i in databaseCursor.description[1:]]) + f" WHERE code = '{infoLocal[0]}'", infoLocal[1:])
            print('done')
    else:
        print(f'inserting {infoName} info {infoLocal[nameColumnIndex]} ... ', end='')
        databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
        print('done')

infoName, nameColumnIndex, info = infoList[7]
databaseCursor.execute(f'SELECT code FROM {infoName}')
infoRemote = databaseCursor.fetchall()
idListRemote = [i[0] for i in infoRemote]
idListLocal = [i[0] for i in info]
outdatedIdList = [i for i in idListRemote if i not in idListLocal]
if(not outdatedIdList):
    for i in outdatedIdList:
        print(f'outdated date in {infoName}, code = {i}, deleting ... ', end='')
        databaseCursor.execute(f'DELETE FROM {infoName} WHERE code = {i}')
        print('done')

infoName, nameColumnIndex, info = infoList[8]
for infoLocal in info:
    if(not databaseCursor.execute(f'SELECT * FROM {infoName} WHERE classId = {infoLocal[0]} AND lessonId = {infoLocal[1]}')):
        print(f'new {infoName} info ... ', end='')
        databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
        print('done')

infoName, nameColumnIndex, info = infoList[8]
databaseCursor.execute(f'SELECT classId, lessonId FROM {infoName}')
infoRemote = databaseCursor.fetchall()
idListRemote = [i for i in infoRemote]
idListLocal = info
outdatedIdList = [i for i in idListRemote if i not in idListLocal]
if(not outdatedIdList):
    for i in outdatedIdList:
        print(f'outdated date in {infoName}, classId = {i[0]}, lessonId = {i[1]}, deleting ... ', end='')
        databaseCursor.execute(f'DELETE FROM {infoName} WHERE classId = {i[0]}, lessonId = {i[1]}')
        print('done')

infoName, nameColumnIndex, info = infoList[9]
for infoLocal in info:
    if(databaseCursor.execute(f'SELECT * FROM {infoName} WHERE lessonId = {infoLocal[0]} AND teacherId = {infoLocal[1]}')):
        infoRemote = databaseCursor.fetchone()
        if(infoRemote != infoLocal):
            print(f'updating {infoName} info for lessonId: {infoLocal[0]}, teacherId: {infoLocal[1]} ... ', end='')
            databaseCursor.execute(f'UPDATE {infoName} SET ' + ', '.join([f'{i[0]} = %s' for i in databaseCursor.description[1:]]) + f' WHERE lessonId = {infoLocal[0]} AND teacherId = {infoLocal[1]}', infoLocal[1:])
            print('done')
    else:
        print(f'new {infoName} info ... ', end='')
        databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
        print('done')

infoName, nameColumnIndex, info = infoList[9]
databaseCursor.execute(f'SELECT lessonId, teacherId FROM {infoName}')
infoRemote = databaseCursor.fetchall()
idListRemote = [i for i in infoRemote]
idListLocal = [(i[0], i[1]) for i in info]
outdatedIdList = [i for i in idListRemote if i not in idListLocal]
if(not outdatedIdList):
    for i in outdatedIdList:
        print(f'outdated date in {infoName}, lessonId = {i[0]}, teacherId = {i[1]}, deleting ... ', end='')
        databaseCursor.execute(f'DELETE FROM {infoName} WHERE lessonId = {i[0]}, teacherId = {i[1]}')
        print('done')

infoName, nameColumnIndex, info = infoList[10]
for infoLocal in info:
    if(not databaseCursor.execute(f"SELECT * FROM {infoName} WHERE classmateCode = '{infoLocal[0]}' AND lessonId = {infoLocal[1]}")):
        print(f'new {infoName} info ... ', end='')
        databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
        print('done')

infoName, nameColumnIndex, info = infoList[10]
databaseCursor.execute(f'SELECT classmateCode, lessonId FROM {infoName}')
infoRemote = databaseCursor.fetchall()
idListRemote = [i for i in infoRemote]
idListLocal = info
outdatedIdList = [i for i in idListRemote if i not in idListLocal]
if(not outdatedIdList):
    for i in outdatedIdList:
        print(f'outdated date in {infoName}, classmateCode = {i[0]}, lessonId = {i[1]}, deleting ... ', end='')
        databaseCursor.execute(f'DELETE FROM {infoName} WHERE classmateCode = {i[0]}, lessonId = {i[1]}')
        print('done')

infoName, nameColumnIndex, info = infoList[11]
for infoLocal in info:
    if(databaseCursor.execute(f"SELECT * FROM {infoName} WHERE lessonId = {infoLocal[0]} AND startDate = '{infoLocal[2]}' AND startTime = {infoLocal[3]}")):
        infoRemote = databaseCursor.fetchone()
        infoRemoteProcessed = list(infoRemote)
        infoRemoteProcessed[2] = str(infoRemote[2])
        infoRemoteProcessed[3] = int(str(infoRemote[3]).replace(':', ''))
        infoRemoteProcessed[4] = int(str(infoRemote[4]).replace(':', ''))
        infoLocalProcessed = list(infoLocal)
        if(infoRemoteProcessed != infoLocalProcessed):
            print(f'updating {infoName} info for lessonId: {infoLocal[0]} ... ', end='')
            databaseCursor.execute(f'UPDATE {infoName} SET ' + ', '.join([f'{i[0]} = %s' for i in databaseCursor.description[1:]]) + f" WHERE lessonId = {infoLocal[0]} AND startDate = '{infoLocal[2]}' AND startTime = {infoLocal[3]}", infoLocal[1:])
            print('done')
    else:
        print(f'new {infoName} info ... ', end='')
        databaseCursor.execute(f'INSERT INTO {infoName} VALUES (' + ', '.join(['%s' for i in databaseCursor.description]) + ')', infoLocal)
        print('done')

infoName, nameColumnIndex, info = infoList[11]
databaseCursor.execute(f'SELECT lessonId, startDate, startTime FROM {infoName}')
infoRemote = databaseCursor.fetchall()
idListRemote = [(i[0], str(i[1]), int(str(i[2]).replace(':', ''))) for i in infoRemote]
idListLocal = [(i[0], i[2], i[3]) for i in info]
outdatedIdList = [i for i in idListRemote if i not in idListLocal]
if(not outdatedIdList):
    for i in outdatedIdList:
        print(f'outdated date in {infoName}, lessonId = {i[0]}, date = {i[1]}, deleting ... ', end='')
        databaseCursor.execute(f"DELETE FROM {infoName} WHERE lessonId = {i[0]}, startDate = '{i[1]}' AND startTime = {i[2]}")
        print('done')

databaseCursor.close()
connection.close()