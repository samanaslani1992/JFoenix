/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.jfoenix.skins;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.effects.JFXDepthManager;
import com.sun.javafx.scene.control.skin.ListViewSkin;

import javafx.application.Platform;
import javafx.scene.control.ListCell;

/**
 * @author Shadi Shaheen
 *
 */
public class JFXListViewSkin<T> extends  ListViewSkin<T>{

    public JFXListViewSkin(final JFXListView<T> listView) {
        super(listView);
        JFXDepthManager.setDepth(flow, listView.depthProperty().get());
        listView.depthProperty().addListener((o,oldVal,newVal)->JFXDepthManager.setDepth(flow, newVal));
//        flow.setCreateCell(flow1 -> JFXListViewSkin.this.createCell());        
    }
    
//    @Override protected void layoutChildren(final double x, final double y,
//            final double w, final double h) {
//    	super.layoutChildren(x, y, w, h);
////    	if (getItemCount() != 0) {
////    		 double estimatedHeight = estimateHeight();
////             if(flow.getCellCount() > 0 && estimatedHeight < getSkinnable().getPrefHeight())
////             	getSkinnable().setPrefHeight(estimatedHeight); 
////    	}
//    }
    
    @Override protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
    	if(getSkinnable().maxHeightProperty().isBound() || getSkinnable().getItems().size() <= 0) return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    	if(getSkinnable().getMaxHeight() > 0) return getSkinnable().getMaxHeight();
    	Platform.runLater(()->getSkinnable().requestLayout());
        return estimateHeight();
    }
    
    private double estimateHeight(){
    	// compute the border/padding for the list
    	double borderWidth = getSkinnable().snappedBottomInset() + getSkinnable().snappedTopInset();
//        if(getSkinnable().getBorder()!=null) {
//        	borderWidth += Math.ceil(getSkinnable().getBorder().getStrokes().get(0).getWidths().getTop());
//        	borderWidth += Math.ceil(getSkinnable().getBorder().getStrokes().get(0).getWidths().getBottom());
//        }
//        if(getSkinnable().getPadding()!=null){
//        	borderWidth += getSkinnable().getPadding().getTop();
//        	borderWidth += getSkinnable().getPadding().getBottom();
//        }
        // compute the gap between list cells
    	double gap = ((JFXListView<T>) getSkinnable()).currentVerticalGapProperty().get() * (getSkinnable().getItems().size() - 1);
        // compute the height of each list cell
    	double cellsHeight = 0;
    	for(int i = 0 ; i < flow.getCellCount(); i++){
    		ListCell<T> cell = flow.getCell(i);
//    		double cellBorderWidth = 0;
//    		if(flow.getCell(i).getBorder()!=null){
//    			cellBorderWidth += flow.getCell(i).getBorder().getStrokes().get(0).getWidths().getTop();
//    			cellBorderWidth += flow.getCell(i).getBorder().getStrokes().get(0).getWidths().getBottom();
//    		}
    		cellsHeight+= flow.getCell(i).getHeight() + cell.snappedTopInset() + cell.snappedBottomInset();
    	}
//    	double cellsHeight = IntStream.range(0, flow.getCellCount()).mapToDouble(index->{
//    		double cellBorderWidth = 0;
//    		if(flow.getCell(index).getBorder()!=null){
//    			cellBorderWidth += flow.getCell(index).getBorder().getStrokes().get(0).getWidths().getTop();
//    			cellBorderWidth += flow.getCell(index).getBorder().getStrokes().get(0).getWidths().getBottom();
//    		}
//    		return flow.getCell(index).getHeight() + cellBorderWidth;
//    	}).sum();
    	return cellsHeight + gap + borderWidth;
    }
    
    
    
}
