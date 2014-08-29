package io.evercam.androidapp.tasks;

import io.evercam.androidapp.R;
import io.evercam.androidapp.custom.CustomProgressDialog;
import io.evercam.androidapp.custom.CustomToast;
import io.evercam.androidapp.custom.CustomedDialog;
import io.evercam.androidapp.utils.Commons;

import java.util.ArrayList;

import org.apache.http.cookie.Cookie;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class TestSnapshotTask extends AsyncTask<Void, Void, Drawable>
{
	private final String TAG = "evercamplay-TestSnapshotTask";
	private String url;
	private String username;
	private String password;
	private Activity activity;
	private CustomProgressDialog customProgressDialog;

	public TestSnapshotTask(String url, String username, String password, Activity activity)
	{
		this.url = url;
		this.username = username;
		this.password = password;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute()
	{
		customProgressDialog = new CustomProgressDialog(activity);
		customProgressDialog.show(activity.getString(R.string.retrieving_snapshot));
	}

	@Override
	protected Drawable doInBackground(Void... params)
	{
		ArrayList<Cookie> cookies = new ArrayList<Cookie>();
		try
		{
			return Commons.getDrawablefromUrlAuthenticated(url, username, password, cookies, 3000);
		}
		catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(Drawable drawable)
	{
		customProgressDialog.dismiss();

		if (drawable != null)
		{
			CustomToast.showInBottom(activity, R.string.snapshot_test_success);
			CustomedDialog.getSnapshotDialog(activity, drawable).show();
		}
		else
		{
			CustomToast.showInCenter(activity, R.string.snapshot_test_failed);
		}
	}
}
