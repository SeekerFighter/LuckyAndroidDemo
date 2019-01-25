package com.seeker.lucky.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.seeker.lucky.R;
import com.seeker.lucky.app.LuckyFragmentActivity;
import com.seeker.lucky.extensions.LuckyCommon;
import com.seeker.lucky.log.LuckyLogger;
import com.seeker.lucky.widget.group.GroupItem;
import com.seeker.lucky.widget.group.GroupScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Seeker
 * @date 2019/1/25/025  10:14
 */
public class GroupScrollViewActivity extends LuckyFragmentActivity {

    private GroupScrollView groupScrollView;

    private Random random = new Random();

    @Override
    public int layoutResId() {
        return R.layout.activity_groupscrollview;
    }

    @Override
    public void onViewCreated(@NotNull View contentView) {

        groupScrollView = findViewById(R.id.groupScrollView);

        groupScrollView.addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("0000","请输入"),"0000"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("1111","请输入"),"1111"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("2222","请输入"),"2222"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("3333","请输入"),"3333"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("4444","请输入"),"4444"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("5555","请输入"),"5555"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("6666","请输入"),"6666"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("7777","请输入"),"7777"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("8888","请输入"),"8888"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("9999","请输入"),"9999"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("AAAA","请输入"),"AAAA"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("BBBB","请输入"),"BBBB"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("CCCC","请输入"),"CCCC"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("DDDD","请输入"),"DDDD"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("EEEE","请输入"),"EEEE"))
                .addGroupItem(new EditGroupItem(new EditGroupItem.EditTip("FFFF","请输入"),"FFFF"));

        findViewById(R.id.getGroupResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuckyCommon.mapLog(groupScrollView.getGroupActionResponse());
            }
        });

        findViewById(R.id.getSingleResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = random.nextInt(16);
                LuckyLogger.INSTANCE.d("打印第"+index+"结果:");
                LuckyCommon.mapLog(groupScrollView.getItemActionResponse(index));
            }
        });

    }


    public static final class EditGroupItem extends GroupItem<EditGroupItem.EditTip>{

        private Map<String,String> result = new HashMap<>();

        private String key;

        private EditText inputEdit;

        EditGroupItem(EditTip source,String key) {
            super(source);
            this.key = key;
        }

        @Override
        public int layoutResId() {
            return R.layout.item_groupitem;
        }

        @Override
        public void onViewAdded(View itemView) {
            TextView headKey = itemView.findViewById(R.id.headKey);
            inputEdit = itemView.findViewById(R.id.inputEdit);
            headKey.setText(mSource.headKey);
            inputEdit.setHint(mSource.inputHint);
        }

        @Override
        public Map<String, String> doAction() {
            result.put(key,inputEdit.getText().toString());
            return result;
        }


        static final class EditTip{

            private String headKey;

            private String inputHint;

            EditTip(String headKey, String inputHint) {
                this.headKey = headKey;
                this.inputHint = inputHint;
            }
        }

    }



}
