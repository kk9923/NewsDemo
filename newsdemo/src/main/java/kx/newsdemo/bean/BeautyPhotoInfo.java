package kx.newsdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 美女图片
 */
public class BeautyPhotoInfo implements Serializable {

    /**
     * adtype : 0
     * boardid : comment_bbs
     * clkNum : 0
     * digest : 长发美女  郭碧婷
     * docid : BQSOIFA29001IFA3
     * downTimes : 1264
     * img : http://dmr.nosdn.127.net/w_TEGSpvSxskhGnIuNAt5Q==/6896093022273156436.jpg
     * imgType : 0
     * imgsrc : http://dmr.nosdn.127.net/w_TEGSpvSxskhGnIuNAt5Q==/6896093022273156436.jpg
     * picCount : 0
     * pixel : 700*467
     * program : HY
     * prompt : 成功为您推荐20条新内容
     * recType : 0
     * replyCount : 74
     * replyid : BQSOIFA29001IFA3
     * source : 堆糖网
     * title : 长发美女  郭碧婷
     * upTimes : 4808
     */
//    private int adtype;
//    private String boardid;
//    private int clkNum;
//    private String digest;
//    private int downTimes;
//    private int imgType;
//    private int picCount;
    //    private String program;
//    private String prompt;
//    private int recType;
//    private int replyCount;
//    private String replyid;
//    private String source;
//    private int upTimes;
    //    private String img;
    private String imgsrc;
    private String pixel;
    private String docid;
    private String title;
    // 喜欢
    private boolean isLove;
    // 点赞
    private boolean isPraise;
    // 下载
    private boolean isDownload;

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }


    public boolean getIsDownload() {
        return this.isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public boolean getIsPraise() {
        return this.isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
    }

    public boolean getIsLove() {
        return this.isLove;
    }

    public void setIsLove(boolean isLove) {
        this.isLove = isLove;
    }

    public BeautyPhotoInfo() {
    }
}
