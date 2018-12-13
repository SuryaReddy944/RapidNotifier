package com.sg.rapid.Adapters;



import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.Models.AlaramData;

import java.util.Comparator;
import java.util.List;

import SectionedList.Section;

/**
 * Created by Surya on 30/10/18.
 */

public class SectionHeader implements Section<AlaramResponse>, Comparable<SectionHeader> {

  private List<AlaramResponse> childList;
    String sectionText;
    int index;

    public SectionHeader(List<AlaramResponse> childList, String sectionText, int index) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.index = index;
    }

    @Override
    public List<AlaramResponse> getChildItems() {
        return childList;
    }



    public String getSectionText() {
        return sectionText;
    }

    @Override
    public int compareTo(SectionHeader another) {
        if (this.index > another.index) {
            return -1;
        } else {
            return 1;
        }
    }
}