
public class Sanjiaoxing extends Shape{
	private float fleng;
	private float fhigh;
	public Sanjiaoxing(float inleng,float inhigh) {
		this.fleng=inleng;
		this.fhigh=inhigh;
	}
	@Override
	public float Area() {
		// TODO Auto-generated method stub
		return (float) (0.5*fleng*fhigh);
	}

	@Override
	public float Perimeter() {
		// TODO Auto-generated method stub
		return 3*fleng;
	}
	public float getFleng() {
		return fleng;
	}
	public void setFleng(float fleng) {
		this.fleng = fleng;
	}
	public float getFhigh() {
		return fhigh;
	}
	public void setFhigh(float fhigh) {
		this.fhigh = fhigh;
	}

}
