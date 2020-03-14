
public class Juxing extends Shape{
	private float flen;
	private float fwid;
	public Juxing(float inlen,float inwid) {
		this.flen=inlen;
		this.fwid=inwid;
	}
	
	public float getFlen() {
		return flen;
	}
	public void setFlen(float flen) {
		this.flen = flen;
	}
	public float getFwid() {
		return fwid;
	}
	public void setFwid(float fwid) {
		this.fwid = fwid;
	}
	@Override
	public float Area() {
		// TODO Auto-generated method stub
	     return flen*fwid;
	}
	@Override
	public float Perimeter() {
		// TODO Auto-generated method stub
		return 2*(flen+fwid);
	}
	
}
