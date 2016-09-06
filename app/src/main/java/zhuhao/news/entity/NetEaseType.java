package zhuhao.news.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class NetEaseType {
    private ArrayList<TList> tList;

    public NetEaseType(ArrayList<TList> tList) {
        this.tList = tList;
    }

    public ArrayList<TList> gettList() {
        return tList;
    }

    public void settList(ArrayList<TList> tList) {
        this.tList = tList;
    }

    public static class TList implements Serializable {
        private String tname;
        private String tid;

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public TList(String tname, String tid) {
            this.tname = tname;
            this.tid = tid;
        }
    }
}
