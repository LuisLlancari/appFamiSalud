package com.famisalud.famisalud;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
   private int spacing;
   public ItemSpacingDecoration(int spacing) {
      this.spacing = spacing;
   }

   @Override
   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      outRect.right = spacing; // Espacio a la derecha de cada elemento
   }
}
