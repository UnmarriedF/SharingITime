package com.fanbo.sharingitime.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.adapter.ContactListAdapter;
import com.fanbo.sharingitime.adapter.ContactListChoiceAdapter;
import com.fanbo.sharingitime.biz.ContactBiz;
import com.fanbo.sharingitime.entity.UserEntity;

import java.util.List;

public class ContactActivity extends BaseActivity {

    private RecyclerView rvContactList;
    private ContactListAdapter adapter;
    private RecyclerView rvContactChoiceList;
    private RecyclerView.LayoutManager contactListManager;
    private RecyclerView.LayoutManager contactChoiceManager;
    private ContactListChoiceAdapter choiceAdapter;
    private ContactBiz contactBiz;

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
        //联系人列表
        contactListManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvContactList.setLayoutManager(contactListManager);
        rvContactList = (RecyclerView) findViewById(R.id.rv_contact_list);
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
        contactBiz = new ContactBiz();
        loadContactList();
    }

    private void loadContactList() {
        List<UserEntity> userEntityList = contactBiz.getContactList();
        adapter.addData(userEntityList);
    }
}
