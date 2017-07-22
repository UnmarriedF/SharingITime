package com.fanbo.sharingitime.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.adapter.ContactListAdapter;
import com.fanbo.sharingitime.adapter.ContactListChoiceAdapter;
import com.fanbo.sharingitime.adapter.SpaceItemDecoration;
import com.fanbo.sharingitime.biz.ContactBiz;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.https.BmobHttp;
import com.fanbo.sharingitime.util.Const;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 联系人界面
 */
public class ContactActivity extends BaseActivity {

    private RecyclerView rvContactList;
    private ContactListAdapter adapter;
    private RecyclerView rvContactChoiceList;
    private RecyclerView.LayoutManager contactListManager;
    private RecyclerView.LayoutManager contactChoiceManager;
    private ContactListChoiceAdapter choiceAdapter;
    private ContactBiz contactBiz;
    private Handler contactListHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        addListener();
    }

    private void addListener() {

    }

    private void initView() {
        titlebar = (RelativeLayout) findViewById(R.id.title_bar);
        initTitleBar(R.drawable.back,null,"联系人",null,0);
        contactListHandler = new ContactListHandler();
        //联系人列表
        rvContactList = (RecyclerView) findViewById(R.id.rv_contact_list);
        rvContactList.addItemDecoration(new SpaceItemDecoration(20));
        contactListManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvContactList.setLayoutManager(contactListManager);
        adapter = new ContactListAdapter(this);
        rvContactList.setAdapter(adapter);
        //选中联系人列表
        contactChoiceManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rvContactChoiceList = (RecyclerView) findViewById(R.id.rv_contact_choice_list);
        rvContactChoiceList.setLayoutManager(contactChoiceManager);
        choiceAdapter = new ContactListChoiceAdapter(this);
        rvContactChoiceList.setAdapter(choiceAdapter);
        // TODO: 2017/7/2  加载联系人列表 在加载完成前显示加载进度条
        //加载联系人列表
        contactBiz = new ContactBiz(contactListHandler);
        // TODO: 2017/7/4    数据加载完毕，更新recycle
        contactBiz.getContactList();
    }

    class ContactListHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Const.GET_FRIENDS_SUCCESS:
                    adapter.addData((List<UserEntity>) msg.obj);
                    break;
                case Const.GET_FRIENDS_FAILED:
                    // TODO: 2017/7/11 获取好友失败，吐司提示
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        //释放资源
        contactBiz = null;
        super.onDestroy();
    }
}
