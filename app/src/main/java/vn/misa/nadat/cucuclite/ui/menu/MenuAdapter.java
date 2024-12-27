package vn.misa.nadat.cucuclite.ui.menu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.misa.nadat.cucuclite.R;
import vn.misa.nadat.cucuclite.items.ItemMenu;

public class MenuAdapter extends RecyclerView.Adapter {
    private List<ItemMenu> mItemMenus;

    public MenuAdapter(List<ItemMenu> itemMenus) {
        mItemMenus = itemMenus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_menu, viewGroup, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) viewHolder;
        menuViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mItemMenus.size();
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvTitle;

        private MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvMenu);
        }

        private void bind(int position) {
            imgIcon.setImageResource(mItemMenus.get(position).getDrawable());
            tvTitle.setText(mItemMenus.get(position).getTitle());
        }
    }
}
