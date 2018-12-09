package stis.ks2.group2.eventagenda.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

public class HeadGuide implements ParentObject {

    private List<Object> mBodyList;
    private UUID id;
    private String title;


    public HeadGuide(String title) {
        this.title = title;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mBodyList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mBodyList = list;
    }
}
