package iuh.edu;

public class LopHoc {
	
	private String maLop;
	private String tenLop;
	private String dayNha;
	
	public String getMaLop() {
		return maLop;
	}
	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}
	public String getTenLop() {
		return tenLop;
	}
	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	public String getDayNha() {
		return dayNha;
	}
	public void setDayNha(String dayNha) {
		this.dayNha = dayNha;
	}
	public LopHoc(String maLop, String tenLop, String soNha) {
		super();
		this.maLop = maLop;
		this.tenLop = tenLop;
		this.dayNha = soNha;
	}
	
	
}
