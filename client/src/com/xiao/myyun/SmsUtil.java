package com.xiao.myyun;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

import com.xiao.bean.SmsInfo;

public class SmsUtil {

	public static List<SmsInfo> getSmsInfoListFromJsonData(String jsonData) {

		try {
			JSONArray arr = new JSONArray(jsonData);
			List<SmsInfo> list = new ArrayList<SmsInfo>();
			SmsInfo info;
			for (int i = 0; i < arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				info = new SmsInfo();
				info.setAddress(temp.getString("address"));
				info.setBody(temp.getString("body"));
				info.setDate(temp.getLong("date"));
				info.setProtocol(temp.getInt("protocol"));
				info.setRead(temp.getString("read"));
				info.setStatus(temp.getString("status"));
				info.setType(temp.getInt("type"));
				list.add(info);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getContactNameByAddr(Context context,
			String phoneNumber) {

		Uri personUri = Uri.withAppendedPath(
				ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phoneNumber));
		Cursor cur = context.getContentResolver().query(personUri,
				new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);
		if (cur.moveToFirst()) {
			int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String name = cur.getString(nameIdx);
			cur.close();
			return name;
		}
		return phoneNumber;
	}

	public static void insertSms(Context ct, List<SmsInfo> infoList) {
		List<SmsInfo> data = getSmsInfoListFromJsonData(getSmsJsonData(ct));
		if(infoList==null)
			return;
		Uri uri = Uri.parse("content://sms");
		ContentValues value;
		ContentResolver cr = ct.getContentResolver();
		boolean have = false;
		for (SmsInfo si : infoList) {
			have = false; 
			if(data!=null){
				for(SmsInfo si2:data){
					if (si.getAddress().equals(si2.getAddress())
							&& si.getBody().equals(si2.getBody())){
						have = true;
						break;
					} 
				}
			}
			
			if (!have) {
				value = new ContentValues();
				value.put("address", si.getAddress());
				value.put("body", si.getBody());
				value.put("date", si.getDate());
				value.put("type", si.getType());
				value.put("protocol", si.getProtocol());
				value.put("read", si.getRead());
				value.put("status", si.getStatus());
				cr.insert(uri, value);
			}

		}

	}

	public static String getSmsJsonData(Context ct) {
		final String SMS_URI_ALL = "content://sms/";

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		try {
			ContentResolver cr = ct.getContentResolver();
			String[] projection = new String[] { "_id", "thread_id", "address",
					"person", "body", "date", "protocol", "read", "status",
					"type" };
			Uri uri = Uri.parse(SMS_URI_ALL);
			Cursor cur = cr.query(uri, projection, null, null, "date desc");

			if (cur.moveToFirst()) {
				String id;
				String thread_id;
				String name;
				String phoneNumber;
				String smsbody;
				long date;
				int protocol;
				String read;
				String status;
				int type;

				int idColumn = cur.getColumnIndex("_id");
				int thread_idColumn = cur.getColumnIndex("thread_id");
				int phoneNumberColumn = cur.getColumnIndex("address");
				int smsbodyColumn = cur.getColumnIndex("body");
				int dateColumn = cur.getColumnIndex("date");
				int protocolColumn = cur.getColumnIndex("protocol");
				int readColumn = cur.getColumnIndex("read");
				int statusColumn = cur.getColumnIndex("status");
				int typeColumn = cur.getColumnIndex("type");

				do {
					id = cur.getString(idColumn);
					thread_id = cur.getString(thread_idColumn);
					phoneNumber = cur.getString(phoneNumberColumn);
					name = SmsUtil.getContactNameByAddr(ct, phoneNumber);
					smsbody = cur.getString(smsbodyColumn);
					protocol = cur.getInt(protocolColumn);
					read = cur.getString(readColumn);
					status = cur.getString(statusColumn);
					type = cur.getInt(typeColumn);
					date = cur.getLong(dateColumn);
					// create a jsonobject
					jsonObject = new JSONObject();
					jsonObject.put("id", id);
					jsonObject.put("thread_id", thread_id);
					jsonObject.put("address", phoneNumber);
					jsonObject.put("name", name);
					jsonObject.put("body", smsbody);
					jsonObject.put("protocol", protocol);
					jsonObject.put("read", read);
					jsonObject.put("status", status);
					jsonObject.put("type", type);
					jsonObject.put("date", date);
					// put a jsonArray
					jsonArray.put(jsonObject);

				} while (cur.moveToNext());
			} else {
				return "";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jsonArray.toString();
	}

}
