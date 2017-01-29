package entity;

import java.io.Serializable;

//文件实体类
public class File implements Serializable{
	
	private static final long serialVersionUID=1254554707571682105L;//用于对类进行版本控制的;
	private int fid;//文件编号
	private String fname;//文件名
	private byte[] fcontent;//文件内容
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public byte[] getFcontent() {
		return fcontent;
	}
	public void setFocntent(byte[] fcontent) {
		this.fcontent = fcontent;
	}
	
	public File(int fid, String fname, byte[] fcontent) {
		this.fid = fid;
		this.fname = fname;
		this.fcontent = fcontent;
	}
	public File(String fname,byte[] fcontent){
		this.fname=fname;
		this.fcontent=fcontent;
	}
	
	
}
