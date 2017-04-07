package robert.com.baselibrary;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Created by LL on 15/12/9.
 *
 * 基类的dialogfragment
 */
public class LTBaseDialogFragment extends DialogFragment implements View.OnClickListener {

    public LTBaseDialogFragment() {
        super();
    }

    public View getRootView() {
        return rootView;
    }

    private View rootView;

    @Override
    public void onClick(View v) {
        if (null != dialogFragmentClickListern)
        {
            dialogFragmentClickListern.dialogBtnClick(v,this);
        }
    }

    public abstract interface LTDialogFragmentUIDataSource
    {
        abstract public int dialogGetLayoutId();

        abstract public void dialogSetView(View view);
    }

    public interface LTDialogFragmentClickListen
    {
        public void dialogBtnClick(View view, LTBaseDialogFragment fragment);
    }

    LTDialogFragmentUIDataSource uiDataSource;

    public String getDialogTag() {
        return tag;
    }

    public void setDialogTag(String tag) {
        this.tag = tag;
    }

    private String tag;


    public void setDialogFragmentClickListern(LTDialogFragmentClickListen dialogFragmentClickListern) {
        this.dialogFragmentClickListern = dialogFragmentClickListern;
    }

    LTDialogFragmentClickListen dialogFragmentClickListern;

    public LTBaseDialogFragment(LTDialogFragmentUIDataSource uiDataSource, String tag)
    {
        super();
        this.uiDataSource = uiDataSource;
        this.setDialogTag(tag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (null == this.uiDataSource){
            throw new Error("please init uiDataSource");
        }
        rootView = inflater.inflate(this.uiDataSource.dialogGetLayoutId(), container);
        setClickEvent(rootView);
        uiDataSource.dialogSetView(rootView);
        return rootView;
    }

    private void setClickEvent(View view)
    {
        if (view instanceof ViewGroup)
        {
            ViewGroup mLayout = (ViewGroup) view;
            for(int i = 0; i < mLayout.getChildCount(); i++)
            {
                if ( mLayout.getChildAt(i) instanceof Button)
                {
                    Button button = (Button) mLayout.getChildAt(i);
                    button.setOnClickListener(this);
                }
                    //兼容广告
                else if ( mLayout.getChildAt(i) instanceof ImageView)
                {
                    ImageView imageView = (ImageView) mLayout.getChildAt(i);
                    imageView.setOnClickListener(this);
                }else if ( mLayout.getChildAt(i) instanceof ViewGroup)
                {
                    setClickEvent(mLayout.getChildAt(i));
                }

            }

        }else {
            throw new Error("dialog layout must be a view group");
        }

    }
}
