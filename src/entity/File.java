package entity;

import java.io.Serializable;

//�ļ�ʵ����
public class File implements Serializable{
	
	private static final long serialVersionUID=1254554707571682105L;//���ڶ�����а汾���Ƶ�;
	private int fid;//�ļ����
	private String fname;//�ļ���
	private byte[] fcontent;//�ļ�����
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
