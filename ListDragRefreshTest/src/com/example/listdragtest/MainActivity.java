package com.example.listdragtest;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.example.listdragtest.lib.DragSortListView;
import com.example.listdragtest.lib.DragSortListView.DragListener;
import com.example.listdragtest.lib.SimpleDragSortCursorAdapter;

public class MainActivity extends ListActivity {

	private SimpleDragSortCursorAdapter adapter;
	DragSortListView dslv;
	private PtrClassicFrameLayout mPtrFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		String[] cols = { "name" };
		int[] ids = { R.id.text };
		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.list_view_with_empty_view_fragment_ptr_frame);
		initPtr();

		adapter = new MAdapter(this, R.layout.list_item_click_remove, null,
				cols, ids, 0);
		dslv = (DragSortListView) findViewById(android.R.id.list);
		dslv.setAdapter(adapter);
		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		dslv.setRefreshView(mPtrFrame);
		mPtrFrame.setPullToRefresh(true);
		dslv.setDragListener(new DragListener() {

			@Override
			public void drag(int from, int to) {
				// TODO Auto-generated method stub

			}
		});
		// build a cursor from the String array
		MatrixCursor cursor = new MatrixCursor(new String[] { "_id", "name" });
		String[] artistNames = getResources().getStringArray(
				R.array.jazz_artist_names);
		for (int i = 0; i < artistNames.length; i++) {
			cursor.newRow().add(i).add(artistNames[i]);
		}
		adapter.changeCursor(cursor);
	}

	private void initPtr() {
		// TODO Auto-generated method stub
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {

				// here check $mListView instead of $content
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						dslv, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				updateData();
			}

		});
		// the following are default settings
		mPtrFrame.setResistance(1.7f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(1000);
		// default is false
		// mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
	}

	private void updateData() {
		// TODO Auto-generated method stub

	}

	private class MAdapter extends SimpleDragSortCursorAdapter {
		private Context mContext;

		public MAdapter(Context ctxt, int rmid, Cursor c, String[] cols,
				int[] ids, int something) {
			super(ctxt, rmid, c, cols, ids, something);
			mContext = ctxt;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			View tv = v.findViewById(R.id.text);
			/*
			 * tv.setOnClickListener(new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { Toast.makeText(mContext,
			 * "text clicked", Toast.LENGTH_SHORT) .show(); } });
			 */
			return v;
		}
	}

}
