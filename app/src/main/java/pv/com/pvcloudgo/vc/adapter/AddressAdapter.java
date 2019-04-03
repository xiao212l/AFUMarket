package pv.com.pvcloudgo.vc.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.model.bean.Address;
import pv.com.pvcloudgo.model.bean.addressBean;


/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class AddressAdapter extends SimpleAdapter<addressBean> {



    private  AddressLisneter lisneter;

    public AddressAdapter(Context context, List<addressBean> datas,AddressLisneter lisneter) {
        super(context, R.layout.template_address,datas);

        this.lisneter = lisneter;


    }


    @Override
    protected void convert(BaseViewHolder viewHoder, final addressBean item) {

        viewHoder.getTextView(R.id.txt_name).setText(item.getReceiverName());
        viewHoder.getTextView(R.id.txt_phone).setText(replacePhoneNum(item.getReceiverPhone()));
        viewHoder.getTextView(R.id.txt_province).setText(item.getReceiverProvince()==null? "" : item.getReceiverProvince());
        viewHoder.getTextView(R.id.txt_city).setText(item.getReceiverCity()==null? "" : item.getReceiverCity());
        viewHoder.getTextView(R.id.txt_district).setText(item.getReceiverDistrict()==null? "" : item.getReceiverDistrict());
        viewHoder.getTextView(R.id.txt_address).setText(item.getReceiverAddress());


//        final CheckBox checkBox = viewHoder.getCheckBox(R.id.cb_is_defualt);

        final boolean isDefault = false;
//        checkBox.setChecked(isDefault);

//        if(isDefault){
//            checkBox.setText("默认地址");
//        }
//        else{
//
//            checkBox.setClickable(true);
//
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if(isChecked && lisneter !=null){
//
//                        lisneter.setDefault(item);
//
//                    }
//                }
//            });
//
//
//        }


    }



    public String replacePhoneNum(String phone){

        return phone.substring(0,phone.length()-(phone.substring(3)).length())+"****"+phone.substring(7);
    }


   public interface AddressLisneter{


        void setDefault(Address address);
       void setDefault(addressBean address);
    }

}
