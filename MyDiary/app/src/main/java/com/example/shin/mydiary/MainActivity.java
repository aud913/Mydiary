package com.example.shin.mydiary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements OnClickListener, AdapterView.OnItemClickListener {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //private TextView tvDate;
    private GridAdapter gridAdapter;
    private ArrayList<String> mItems;
    private GridView gridView;
    private Calendar mCal;
    private ArrayAdapter<String> adapter;
    private TextView textYear;
    private TextView textMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }


        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);

        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);

        //tvDate = (TextView)findViewById(R.id.tv_date);
        gridView = (GridView)findViewById(R.id.gridview);
        GridView gird = (GridView) this.findViewById(R.id.gridview);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        //final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        //tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));
        //dayList = new ArrayList<String>();


        mCal = Calendar.getInstance();
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i < dayNum; i++) {
            mItems.add("");
        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);

        gridAdapter = new GridAdapter(getApplicationContext(), mItems);
        gridView.setAdapter(gridAdapter);

        Date date1 = new Date();// ���ÿ� ��¥�� ���� ���ش�.
        int year = date1.getYear() + 1900;
        int mon = date1.getMonth() + 1;
        textYear.setText(year + "");
        textMon.setText(mon + "");

        Button btnmove = (Button) this.findViewById(R.id.bt1);
        btnmove.setOnClickListener(this);
    }
    private void initialize()
    {
        InitializationRunnable init = new InitializationRunnable();
        new Thread(init).start();
    }
    class InitializationRunnable implements Runnable
    {
        public void run()
        {
            // 여기서부터 초기화 작업 처리
            // do_something
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.m1:
                Intent intent = new Intent(this, Day.class);
                startActivity(intent);


                return true;
           /* case R.id.m2:

                return true;
            case R.id.m3:

                return true;*/
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (arg0.getId() == R.id.bt1) {
            int year = Integer.parseInt(textYear.getText().toString());
            int mon = Integer.parseInt(textMon.getText().toString());
            fillDate(year, mon);
        }
    }

    private void fillDate(int year, int mon) {
        mItems.clear();
        mItems.add("일");
        mItems.add("월");
        mItems.add("화");
        mItems.add("수");
        mItems.add("목");
        mItems.add("금");
        mItems.add("토");

        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // ���ϵ� int�� ����.

        for (int i = 0; i < day; i++) {
            mItems.add("");
        }

        current.setDate(32);// 32�ϱ��� �Է��ϸ� 1�Ϸ� �ٲ��ش�.
        int last = 32 - current.getDate();

        for (int i = 1; i <= last; i++) {
            mItems.add(i + "");
        }
        adapter.notifyDataSetChanged();
    }

    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            mItems.add("" + (i + 1));
        }
    }

    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;
        /**
         * ������
         *
         * @param context
         * @param list
         */
        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvItemGridView.setText("" + getItem(position));

            mCal = Calendar.getInstance();
            return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (mItems.get(arg2).equals("")) {
            ;
        } else {
            Intent intent = new Intent(this, Plus.class);
            intent.putExtra("Param1", textYear.getText().toString() + "/"
                    + textMon.getText().toString() + "/" + mItems.get(arg2));
            startActivity(intent);
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }
}


