package com.remswork.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.remswork.classmanager.R;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.utils.StringFormatter;

import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael on 7/24/2017.
 */

public class ClazzRecyclerViewAdapter extends RecyclerView.Adapter<
        ClazzRecyclerViewAdapter.ClazzViewHolder>{

    private Context context;
    private List<Clazz> clazzes;
    private LayoutInflater inflater;
    private ClazzRecyclerViewAdapterListener clazzRecyclerViewAdapterListener;

    public ClazzRecyclerViewAdapter(Context context, List<Clazz> clazzes){
        this.context = context;
        this.clazzes = clazzes;
        inflater = LayoutInflater.from(context);
        clazzRecyclerViewAdapterListener = (ClazzRecyclerViewAdapterListener) context;
    }

    @Override
    public ClazzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = inflater.inflate(
                R.layout.fragment_slidebar_cardview_clazz, parent, false);
        ClazzViewHolder clazzViewHolder = new ClazzViewHolder(customView);
        return clazzViewHolder;
    }

    @Override
    public void onBindViewHolder(ClazzViewHolder clazzViewHolder, int position) {
        Clazz clazz = clazzes.get(position);
        clazzViewHolder.setView(clazz, position);
    }

    @Override
    public int getItemCount() {
        return clazzes.size();
    }

    public void addItem(final Clazz clazz){
        clazzes.add(clazz);
        notifyItemInserted(clazzes.size());
        notifyItemRangeChanged(clazzes.size(), clazzes.size());
    }

    private void deleteItem(final int position){
        clazzes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clazzes.size());
    }

    protected class ClazzViewHolder extends RecyclerView.ViewHolder implements PopupMenu
            .OnMenuItemClickListener, View.OnClickListener{

        private ImageView imageViewHeader;
        private ImageView imageViewOverflow;
        private TextView textSection;
        private TextView textSubjectName;
        private Clazz clazz;
        private RelativeLayout imageViewOverflowButtonAssistant;
        private int position;

        public ClazzViewHolder(View view){
            super(view);
            imageViewHeader = (ImageView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_clazz_imageview);
            imageViewOverflow = (ImageView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_clazz_imageview_overflow);
            imageViewOverflowButtonAssistant = (RelativeLayout) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_clazz_imageview_overflow_r);
            textSection = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_clazz_text_section);
            textSubjectName = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_clazz_text_subject);
        }


        public void setView(Clazz currentClazz, int position) {
            this.clazz = currentClazz;
            this.position = position;
            imageViewHeader.setOnClickListener(this);
            textSubjectName.setText(clazz.getSubject() != null ? StringFormatter.cutLongString(
                    clazz.getSubject().getName(), 17) : "No subject");
            textSection.setText(clazz.getSection() != null ? String.format(Locale.ENGLISH,
                    "%s-%d%s", clazz.getSection().getDepartment(), clazz.getSection().getYear(),
                    clazz.getSection().getSectionName()) : "No section detail");

            imageViewOverflowButtonAssistant.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showPopupMenu(imageViewOverflowButtonAssistant);
                }
            });
        }

        public void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(context, view);
            MenuInflater menuInflater = popupMenu.getMenuInflater();
            menuInflater.inflate(R.menu.cardview_option_menu_clazz, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.cardview_clazz_popup_delete :
                    deleteItem(position);
                    clazzRecyclerViewAdapterListener.deleteClazz(clazz.getId());
                    break;
                default:
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            clazzRecyclerViewAdapterListener.viewClazz(clazz);
        }

    }

    public interface ClazzRecyclerViewAdapterListener{
        void viewClazz(Clazz clazz);
        void deleteClazz(int clazzId);
    }
}
