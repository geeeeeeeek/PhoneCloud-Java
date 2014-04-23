package com.xiao.myyun;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray; 
import org.json.JSONObject; 
import com.xiao.bean.ContactInfo;  
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context; 
import android.database.Cursor;
import android.net.Uri; 
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts; 

public class ContactUtil {
    /**
     * 获取联系人的json
     * @param ct
     * @return
     */
	public static String getContactJsonData(Context ct)   {
		int contactid;
		String name;
		String phone = "";
		String number;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		ContentResolver resolver = ct.getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		Cursor phoneCursor;
		try{
			while (cursor.moveToNext()) {
				jsonObject = new JSONObject();
				contactid = cursor.getInt(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				name = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				jsonObject.put("name", name);
				phoneCursor = resolver.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
						new String[] { String.valueOf(contactid) }, null);
				while (phoneCursor.moveToNext()) {
					number = phoneCursor
							.getString(phoneCursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					phone = phone + "#" + number;
				}
				if (phone.equals("")) {
					jsonObject.put("phone", phone);
				} else {
					jsonObject.put("phone", phone.substring(1));
				}
				phone = "";
				jsonArray.put(jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonArray.toString();
	}

	/**
	 * 解析json数据，获取所有的联系人
	 * @param jsonData
	 * @return
	 */
	public static List<ContactInfo> getContactListFromJsonData(String jsonData) {
		try {
			JSONArray arr = new JSONArray(jsonData);
			List<ContactInfo> list = new ArrayList<ContactInfo>();
			ContactInfo contactInfo;
			for (int i = 0; i < arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				contactInfo = new ContactInfo();
				contactInfo.setName(temp.getString("name"));
				contactInfo.setPhone(temp.getString("phone"));
				list.add(contactInfo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将联系人插入手机通讯录
	 * @param ct
	 * @param contactList
	 */
	public static void insertContact(Context ct, List<ContactInfo> contactList) { 
		ContentValues values;
		Uri rawContactUri;
		long rawContactId ;
		boolean have=false;
		ContentResolver resolver=ct.getContentResolver();
		List<ContactInfo> contactList2=getContactListFromJsonData(getContactJsonData(ct));
		for (ContactInfo contactInfo : contactList) {
			have=false;
			for(ContactInfo contactInfo2:contactList2){
				if(contactInfo.getName().equals(contactInfo2.getName())&&contactInfo.getPhone().equals(contactInfo2.getPhone())){
					{
						have=true;
						break;
					}
				}
			}
			if(!have){
				values = new ContentValues();
				//插入空值，获取rawContactId
				rawContactUri =resolver.insert(RawContacts.CONTENT_URI, values);
				rawContactId = ContentUris.parseId(rawContactUri);
				
				values.clear();
				values.put(Data.RAW_CONTACT_ID, rawContactId);
				values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
				values.put(StructuredName.GIVEN_NAME, contactInfo.getName());
				resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
				
				values.clear(); 
				values.put(Data.RAW_CONTACT_ID, rawContactId);
				values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
				values.put(Phone.TYPE, Phone.TYPE_MOBILE);
				if(contactInfo.getPhone().contains("#")){
					String phones[]=contactInfo.getPhone().split("#");
					for(int i=0;i<phones.length;i++){ 
						values.put(Phone.NUMBER,phones[i]); 
						resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
					}
				}else{
					values.put(Phone.NUMBER, contactInfo.getPhone()); 
					resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
				}
				values.clear();
			} 
		} 
	}
}
