package xyz.bnayagrawal.android.packages;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by root on 19/12/17.
 */

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {

    List<ApplicationInfo> apps;
    PackageManager packageManager;
    Context context;
    File file;

    public RAdapter(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.apps = packageManager.getInstalledApplications(packageManager.GET_META_DATA);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_list_item_template, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.imgAppIcon.setImageDrawable(apps.get(i).loadIcon(packageManager));
        viewHolder.txtAppLabel.setText(apps.get(i).loadLabel(packageManager));

        //set app size
        file = new File(apps.get(i).sourceDir);
        double size = file.length();
        if(size < 1024)
            viewHolder.txtAppSize.setText(size  + "bytes");
        else if((size/1024) < 1024)
            viewHolder.txtAppSize.setText(new DecimalFormat("0.00").format((size/1024)) + "Kb");
        else
            viewHolder.txtAppSize.setText(new DecimalFormat("0.00").format((size/1024)/1024) + "Mb");
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAppLabel,txtAppSize;
        ImageView imgAppIcon;

        public ViewHolder(View item) {
            super(item);
            txtAppLabel = (TextView) item.findViewById(R.id.appLabel);
            imgAppIcon = (ImageView) item.findViewById(R.id.appIcon);
            txtAppSize = (TextView) item.findViewById(R.id.appSize);
        }
    }
}
