
public class Circle extends Shape{
	private float fradius;
	public Circle(float inradius) {
		this.fradius=inradius;
	}
	@Override
	public float Area() {
		// TODO Auto-generated method stub
		return (float) (3.14*fradius*fradius);
	}

	@Override
	public float Perimeter() {
		// TODO Auto-generated method stub
		return (float) (3.14*2*fradius);
	}

	public float getFradius() {
		return fradius;
	}

	public void setFradius(float fradius) {
		this.fradius = fradius;
	}

}
