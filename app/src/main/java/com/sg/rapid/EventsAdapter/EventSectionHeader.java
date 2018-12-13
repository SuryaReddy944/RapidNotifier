package com.sg.rapid.EventsAdapter;

import com.sg.rapid.Adapters.SectionHeader;
import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.EventServices.EventsResponse;

import java.util.List;

import SectionedList.Section;

public class EventSectionHeader implements Section<EventsResponse>, Comparable<EventSectionHeader> {

   public  List<EventsResponse> childList;
    String sectionText;
    int index;

    public EventSectionHeader(List<EventsResponse> childList, String sectionText, int index) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.index = index;
    }

    @Override
    public List<EventsResponse> getChildItems() {
        return childList;
    }


    public String getSectionText() {
        return sectionText;
    }

    @Override
    public int compareTo(EventSectionHeader another) {
        if (this.index > another.index) {
            return -1;
        } else {
            return 1;
        }
    }
}