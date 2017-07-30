package com.remswork.classmanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.remswork.classmanager.R;
import com.remswork.classmanager.model.clazz.Subject;

import java.util.List;

/**
 * Created by Rafael on 7/24/2017.
 */

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter
        .SubjectViewHolder> {

    private Context context;
    private List<Subject> subjects;
    private LayoutInflater inflater;
    private SubjectRecyclerViewAdapterListener subjectRecyclerViewAdapterListener;

    public SubjectRecyclerViewAdapter(Context context, List<Subject> subjects){
        this.context = context;
        this.subjects = subjects;
        inflater = LayoutInflater.from(context);
        if(context instanceof SubjectRecyclerViewAdapterListener)
            subjectRecyclerViewAdapterListener = (SubjectRecyclerViewAdapterListener)context;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = inflater.inflate(
                R.layout.fragment_slidebar_cardview_subject, parent, false);
        SubjectViewHolder scheduleViewHolder = new SubjectViewHolder(customView);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.setView(subject, position);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void deleteItem(int position){
        subjects.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, subjects.size());
    }

     protected class SubjectViewHolder extends RecyclerView.ViewHolder implements View
             .OnClickListener{

         private FrameLayout clickableLayout;
         private TextView textTitle;
         private TextView textDescription;
         private RelativeLayout buttonUpdate;
         private RelativeLayout buttonDelete;
         private Subject subject;
         private int position;

         public SubjectViewHolder(View itemView) {
             super(itemView);
             clickableLayout = (FrameLayout) itemView.findViewById(
                     R.id.fragment_slidebar_cardview_subject_layout);
             textTitle = (TextView) itemView.findViewById(
                     R.id.fragment_slidebar_cardview_subject_text_title);
             textDescription = (TextView) itemView.findViewById(
                     R.id.fragment_slidebar_cardview_subject_text_desc);
             buttonDelete = (RelativeLayout) itemView.findViewById(
                     R.id.fragment_slidebar_cardview_subject_button_delete);
             buttonUpdate = (RelativeLayout) itemView.findViewById(
                     R.id.fragment_slidebar_cardview_subject_button_update);
         }

         public void setView(final Subject subject, int position) {
             this.subject = subject;
             this.position = position;
             textTitle.setText(formatString(subject.getName(), 30));
             textDescription.setText(formatString(subject.getDesc(), 40));
             clickableLayout.setOnClickListener(this);
             buttonDelete.setOnClickListener(this);
             buttonUpdate.setOnClickListener(this);
             setBackgroundByPosition(position);
         }

         @Override
         public void onClick(View v) {
             switch (v.getId()) {
                 case R.id.fragment_slidebar_cardview_subject_button_delete :
                     deleteDialog().show();
                     break;
                 case R.id.fragment_slidebar_cardview_subject_button_update :
                     subjectRecyclerViewAdapterListener.updateSubject(subject.getId(), subject);
                     break;
                 case R.id.fragment_slidebar_cardview_subject_layout :
                     subjectRecyclerViewAdapterListener.openSubject(subject);
                     break;
                 default:
                     break;
             }
         }

         public void setBackgroundByPosition(final int position){
             if(position%2==0) {
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     clickableLayout.setBackground(context
                             .getDrawable(R.drawable.custom_ripple_button_subject_moca1));
                 }
             }
         }

         public String formatString(final String string, final int limit){
             if(string.toCharArray().length < limit)
                 return string;
             else {
                 return string.substring(0, limit) + "...";
             }
         }

         private AlertDialog deleteDialog() {
             return new AlertDialog.Builder(context)
                     .setTitle("Subject : " + subject.getName())
                     .setMessage("Do you want to delete")
                     .setIcon(R.drawable.icon_delete)
                     .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                             deleteItem(position);
                             subjectRecyclerViewAdapterListener.deleteSubject(subject.getId());
                             dialog.dismiss();
                         }
                     })
                     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                         }
                     })
                     .create();
         }
     }

    public interface SubjectRecyclerViewAdapterListener{
        void openSubject(Subject subject);
        void deleteSubject(int id);
        void updateSubject(int id, Subject newSubject);
    }
}
