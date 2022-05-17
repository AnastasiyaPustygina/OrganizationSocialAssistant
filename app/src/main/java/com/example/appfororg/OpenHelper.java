package com.example.appfororg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appfororg.domain.Chat;
import com.example.appfororg.domain.Message;
import com.example.appfororg.domain.Organization;
import com.example.appfororg.domain.Person;

import java.util.ArrayList;

public class OpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_PERSON_NAME = "person";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PERSON_ID = "idPer";
    public static final String TABLE_ORG_NAME = "organization";
    public static final String COLUMN_ORGANIZATION_ID = "idOrg";
    public static final String COLUMN_ORGNAME = "orgName";
    public static final String COLUMN_ORGLOG = "orgLog";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PHOTOORG = "OrganizationPhoto";
    public static final String COLUMN_PHOTOPER = "PersonPhoto";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_NEEDS = "needs";
    public static final String COLUMN_LINKWEB = "linkToWebsite";
    public static final String COLUMN_PASS = "password";
    public static final String TABLE_CHAT_NAME = "chat";
    public static final String TABLE_MSG_NAME = "message";
    public static final String COLUMN_MSG_WHOSE = "whoseMsg";
    public static final String COLUMN_CHAT_ID = "idChat";
    public static final String COLUMN_MSG_CHAT_ID = "idChat";
    public static final String COLUMN_MSG_ID = "idMsg";
    public static final String COLUMN_MSG_VALUE = "msgValue";
    public static final String COLUMN_MSG_TIME = "msgTime";
    public static final String COLUMN_PERSON_CHAT_ID = "idChatPer";
    public static final String COLUMN_ORGANIZATION_CHAT_ID = "idChatOrg";


    public static final int VERSION = 1;


    public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_ORG_NAME + "(" +
                COLUMN_ORGANIZATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORGNAME + " TEXT, " +
                COLUMN_ORGLOG + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PHOTOORG + " BLOB, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_NEEDS + " TEXT, "
                + COLUMN_PASS + " TEXT, "
                + COLUMN_LINKWEB + " TEXT);";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE " + TABLE_CHAT_NAME + "(" +
                COLUMN_ORGANIZATION_CHAT_ID + " INTEGER, " +
                COLUMN_PERSON_CHAT_ID + " INTEGER, "
                + COLUMN_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE " + TABLE_MSG_NAME + "(" +
                COLUMN_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MSG_WHOSE + " TEXT, " +
                COLUMN_MSG_CHAT_ID + " INTEGER, " +
                COLUMN_MSG_VALUE + " TEXT, "
                + COLUMN_MSG_TIME + " TEXT);";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE " + TABLE_PERSON_NAME + "(" +
                COLUMN_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_PHOTOPER + " BLOB, " +
                COLUMN_TELEPHONE + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_DATE_OF_BIRTH + " TEXT, "
                + COLUMN_CITY + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORG_NAME);
        onCreate(db);
    }
    public long insertChat(Chat chat){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PERSON_CHAT_ID, chat.getChat_personId());
        contentValues.put(COLUMN_ORGANIZATION_CHAT_ID, chat.getChat_orgId());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_CHAT_NAME, null, contentValues);
    }
    public long insertMsg(Message msg) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MSG_WHOSE, msg.getWhose());
        contentValues.put(COLUMN_MSG_CHAT_ID, msg.getChat_id());
        contentValues.put(COLUMN_MSG_VALUE, msg.getValues());
        contentValues.put(COLUMN_MSG_TIME, msg.getTime());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_MSG_NAME, null, contentValues);
    }
    public long insertPerson(Person person) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, person.getName());
        contentValues.put(COLUMN_TELEPHONE, person.getTelephone());
        contentValues.put(COLUMN_EMAIL, person.getEmail());
        contentValues.put(COLUMN_PHOTOPER, person.getPhotoPer());
        contentValues.put(COLUMN_CITY, person.getCity());
        contentValues.put(COLUMN_DATE_OF_BIRTH, person.getDateOfBirth());
        contentValues.put(COLUMN_AGE, person.getAge());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_PERSON_NAME, null, contentValues);
    }
    public long insertOrg(Organization org) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORGNAME, org.getName());
        contentValues.put(COLUMN_TYPE, org.getType());
        contentValues.put(COLUMN_PHOTOORG, org.getPhotoOrg());
        contentValues.put(COLUMN_ORGLOG, org.getLogin());
        contentValues.put(COLUMN_DESCRIPTION, org.getDescription());
        contentValues.put(COLUMN_ADDRESS, org.getAddress());
        contentValues.put(COLUMN_NEEDS, org.getNeeds());
        contentValues.put(COLUMN_LINKWEB, org.getLinkToWebsite());
        contentValues.put(COLUMN_PASS, org.getPass());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_ORG_NAME, null, contentValues);
    }
    public int findChatIdByOrgIdAndPerId(int orgId, int perId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAT_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            int currentPerId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSON_CHAT_ID));
            int currentChatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHAT_ID));
            int currentOrgId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZATION_CHAT_ID));
            if(orgId == currentOrgId && perId == currentPerId) return currentChatId;
        }while (cursor.moveToNext());
        return 100;
    }
    public void deleteAllOrganization() {
        SQLiteDatabase mDataBase = getWritableDatabase();
        mDataBase.delete(TABLE_ORG_NAME, null, null);
    }
    public String findPassByLogin(String log){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(TABLE_ORG_NAME, null, null,
                null, null, null, null);
        cur.moveToFirst();
        if(!cur.isAfterLast()){
            do{
                String pass = cur.getString(cur.getColumnIndexOrThrow(COLUMN_PASS));
                String name = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ORGLOG));
                if(name.equals(log)) return pass;
            }while (cur.moveToNext());
        }
        return null;
    }
    public Organization findOrgByLogin(String logOrg){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(TABLE_ORG_NAME, null, null,
                null, null, null, null);
        cur.moveToFirst();
        if(!cur.isAfterLast()){
            do{
                int id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ORGANIZATION_ID));
                String name = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ORGNAME));
                String log = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ORGLOG));
                String type = cur.getString(cur.getColumnIndexOrThrow(COLUMN_TYPE));
                byte[] photo = cur.getBlob(cur.getColumnIndexOrThrow(COLUMN_PHOTOORG));
                String desc = cur.getString(cur.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String address = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String needs = cur.getString(cur.getColumnIndexOrThrow(COLUMN_NEEDS));
                String pass = cur.getString(cur.getColumnIndexOrThrow(COLUMN_PASS));
                String link = cur.getString(cur.getColumnIndexOrThrow(COLUMN_LINKWEB));

                if(log.equals(logOrg)) {
                    return new Organization(id, name, log, type, BitmapFactory.decodeByteArray(
                            photo, 0, photo.length), desc, address, needs, link, pass);
                }
            }while (cur.moveToNext());
        }
        return new Organization(-1, null, null, null,null,
                null, null, null, null,null);
    }
    public void changeDescByLog(String log, String description){
        SQLiteDatabase db = getReadableDatabase();
                    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                    sqLiteDatabase.execSQL("UPDATE " + TABLE_ORG_NAME + " SET " +
                            COLUMN_DESCRIPTION + " = '" + description + "' WHERE " + COLUMN_ORGLOG
                    + " = '" + log + "'");
    }
    public void changeNeedsByLog(String log, String needs){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE " + TABLE_ORG_NAME + " SET " +
                COLUMN_NEEDS + " = '" + needs + "' WHERE " + COLUMN_ORGLOG
                + " = '" + log + "'");
    }
    public int findPersonIdByChatId(int chatId){
        ArrayList<Message> res = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAT_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            int idPer = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSON_CHAT_ID));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHAT_ID));

            if(chatId == id) return idPer;
        }while (cursor.moveToNext());
        return 100;
    }
    public Organization findOrgById(int idOrg){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(TABLE_ORG_NAME, null, null,
                null, null, null, null);
        cur.moveToFirst();
        if(!cur.isAfterLast()){
            do{
                int id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ORGANIZATION_ID));
                String name = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ORGNAME));
                String log = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ORGLOG));
                String pass = cur.getString(cur.getColumnIndexOrThrow(COLUMN_PASS));
                String type = cur.getString(cur.getColumnIndexOrThrow(COLUMN_TYPE));
                byte[] photo = cur.getBlob(cur.getColumnIndexOrThrow(COLUMN_PHOTOORG));
                String desc = cur.getString(cur.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String address = cur.getString(cur.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String needs = cur.getString(cur.getColumnIndexOrThrow(COLUMN_NEEDS));
                String link = cur.getString(cur.getColumnIndexOrThrow(COLUMN_LINKWEB));

                if(id == idOrg) {
                    return new Organization(id, name, log, type, BitmapFactory.decodeByteArray(photo, 0, photo.length), desc,
                            address, needs, link, pass);
                }
            }while (cur.moveToNext());
        }
        return new Organization(1000, null, null, null,null,
                null, null, null, null, null);
    }
    public Person findPersonByChatId(Integer chat_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAT_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            int currentChatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHAT_ID));
            int perId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSON_CHAT_ID));
            if(currentChatId == chat_id) return findPersonById(perId);
        }while (cursor.moveToNext());
        return new Person(1000, null,null, null,19, null,null);
    }
    public Organization findOrgByChatId(Integer chat_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHAT_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            int currentChatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHAT_ID));
            int orgId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZATION_CHAT_ID));
            if(currentChatId == chat_id) return findOrgById(orgId);
        }while (cursor.moveToNext());
        return new Organization(1000, null, null, null,null,
                null, null, null, null, null);
    }
    public ArrayList<Integer> findLastChatId(String login){
        ArrayList<Integer> arr_chat_id = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MSG_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            Integer chatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_CHAT_ID));
            String log = findOrgByChatId(chatId).getLogin();
            if(log == null) continue;
            if(log.equals(login)) {
                if (arr_chat_id.contains((Object) chatId)) {
                    arr_chat_id.remove((Object) chatId);
                    arr_chat_id.add(chatId);
                } else {
                    arr_chat_id.add(chatId);
                }
            }
        }while (cursor.moveToNext());
        return arr_chat_id;
    }
    public Person findPersonByLogin(String log){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(TABLE_PERSON_NAME, null, null,
                null, null, null, null);
        cur.moveToFirst();
        if(!cur.isAfterLast()){
            do{
                int id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_PERSON_ID));
                byte[] photo = cur.getBlob(cur.getColumnIndexOrThrow(COLUMN_PHOTOPER));
                int age = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_AGE));
                String telephone = cur.getString(cur.getColumnIndexOrThrow(COLUMN_TELEPHONE));
                String email = cur.getString(cur.getColumnIndexOrThrow(COLUMN_EMAIL));
                String dateOfBirth = cur.getString(cur.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH));
                String city = cur.getString(cur.getColumnIndexOrThrow(COLUMN_CITY));
                String name = cur.getString(cur.getColumnIndexOrThrow(COLUMN_NAME));

                if(name.equals(log)) {
                    String data = telephone == null ? email : telephone;
                    return new Person(id, data, name, BitmapFactory.decodeByteArray(
                            photo, 0, photo.length), age, dateOfBirth, city);
                }
            }while (cur.moveToNext());
        }
        return new Person(0, null, null, null, 0, null, null);
    }
    public ArrayList<String> findLastMsgValuesByOrgLog(String log){
        ArrayList<String> arr_msgValue = new ArrayList<>();
        ArrayList<Integer> arr_chat_id = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MSG_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            String msg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_VALUE));
            Integer chatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_CHAT_ID));
            if(log.equals(findOrgByChatId(chatId).getLogin())) {
                if (arr_chat_id.contains((Object) chatId)) {
                    arr_msgValue.remove(arr_chat_id.indexOf(chatId));
                    arr_chat_id.remove((Object) chatId);
                    arr_chat_id.add(chatId);
                    arr_msgValue.add(msg);
                } else {
                    arr_msgValue.add(msg);
                    arr_chat_id.add(chatId);
                }
            }

        }while (cursor.moveToNext());
        return arr_msgValue;
    }
    public ArrayList<Message> findMsgByChatId(int chatId){
        ArrayList<Message> res = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MSG_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            String msg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_VALUE));
            Integer curChatId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_CHAT_ID));
            String whose = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_WHOSE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_TIME));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_ID));
            if(chatId == curChatId) res.add(new Message(id, whose, curChatId, msg, time));
        }while (cursor.moveToNext());
        return res;
    }
    public Person findPersonById(int idPer){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.query(TABLE_PERSON_NAME, null, null,
                null, null, null, null);
        cur.moveToFirst();
        if(!cur.isAfterLast()){
            do{
                int id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_PERSON_ID));
                int age = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_AGE));
                String telephone = cur.getString(cur.getColumnIndexOrThrow(COLUMN_TELEPHONE));
                String email = cur.getString(cur.getColumnIndexOrThrow(COLUMN_EMAIL));
                String dateOfBirth = cur.getString(cur.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH));
                String city = cur.getString(cur.getColumnIndexOrThrow(COLUMN_CITY));
                byte[] photo = cur.getBlob(cur.getColumnIndexOrThrow(COLUMN_PHOTOPER));
                String name = cur.getString(cur.getColumnIndexOrThrow(COLUMN_NAME));

                if(id == idPer) {
                    String data = telephone == null ? email : telephone;
                    return new Person(id, data, name,
                            BitmapFactory.decodeByteArray(photo, 0, photo.length), age,
                            dateOfBirth, city);
                }
            }while (cur.moveToNext());
        }
        return new Person(100, null, null, null, 0, null, null);
    }


    public ArrayList<Organization> findAllOrganizations() {
        ArrayList<Organization> arrOrg = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try{
            Cursor cursor = db.query(TABLE_ORG_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            int columnPassIndex = cursor.getColumnIndex(COLUMN_PASS);
            int columnLogIndex = cursor.getColumnIndex(COLUMN_ORGLOG);
            int columnIdIndex = cursor.getColumnIndex(COLUMN_ORGANIZATION_ID);
            int columnNameIndex = cursor.getColumnIndex(COLUMN_ORGNAME);
            int columnTypeIndex = cursor.getColumnIndex(COLUMN_TYPE);
            int columnPhotoIndex = cursor.getColumnIndex(COLUMN_PHOTOORG);
            int columnDescIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int columnAddressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);
            int columnNeedsIndex = cursor.getColumnIndex(COLUMN_NEEDS);
            int columnLinkIndex = cursor.getColumnIndex(COLUMN_LINKWEB);

            do {
                int id = cursor.getInt(columnIdIndex);
                String pass = cursor.getString(columnPassIndex);
                String log = cursor.getString(columnLogIndex);
                String name = cursor.getString(columnNameIndex);
                String type = cursor.getString(columnTypeIndex);
                byte[] photo = cursor.getBlob(columnPhotoIndex);
                String desc = cursor.getString(columnDescIndex);
                String address = cursor.getString(columnAddressIndex);
                String needs = cursor.getString(columnNeedsIndex);
                String link = cursor.getString(columnLinkIndex);
                arrOrg.add(new Organization(id, name, log, type,
                        BitmapFactory.decodeByteArray(photo, 0, photo.length),
                        desc, address, needs, link, pass));
            }while (cursor.moveToNext());} catch (CursorIndexOutOfBoundsException e){
            Log.e("MY_LOG", "нулевой findAllOrg");
        };
        return arrOrg;

    }
    public ArrayList<String> findAllMsgVal(){
        ArrayList<String> arrMsgVal = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MSG_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            String curMsg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_VALUE));
            arrMsgVal.add(curMsg);
        }while (cursor.moveToNext());
        return arrMsgVal;
    }
    public ArrayList<Integer> findAllChatId(){
        ArrayList<Integer> arrChat = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MSG_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_CHAT_ID));
            arrChat.add(id);
        }while (cursor.moveToNext());
        return arrChat;
    }
}