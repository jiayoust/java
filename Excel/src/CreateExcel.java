//生成Excel的类
import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
public class CreateExcel{
	public void createExcel() {
		 try {
	         // 打开文件
	         WritableWorkbook book = Workbook.createWorkbook(new File("test.xls"));
	         // 生成名为“第一页”的工作表，参数0表示这是第一页
	         WritableSheet sheet = book.createSheet("第一页", 0);
	         WritableFont font1 = new WritableFont(WritableFont.ARIAL,17);
	         WritableCellFormat format1=new WritableCellFormat(font1); 
	         WritableCellFormat format2=new WritableCellFormat();
	         format1.setAlignment(jxl.format.Alignment.CENTRE);
	         format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	         format2.setAlignment(jxl.format.Alignment.CENTRE);
	         
	         Label label=new Label(0,0,"求职简历",format1);
	         sheet.mergeCells(0,0,7,0);
	         sheet.setRowView(0,500);
	         sheet.addCell(label);
	         Label label1 = new Label(0, 1, "姓名");
	         sheet.addCell(label1);
	         Label label2 = new Label(2, 1, "性别");
	         sheet.addCell(label2);
	         Label label3 = new Label(4, 1, "籍贯");
	         sheet.addCell(label3);
	         Label label4 = new Label(0, 2, "出生日期");
	         sheet.addCell(label4);
	         Label label5 = new Label(2, 2, "民族");
	         sheet.addCell(label5);
	         Label label6 = new Label(4, 2, "邮箱");
	         sheet.addCell(label6);
	         Label label7 = new Label(0, 3, "家庭地址");
	         sheet.addCell(label7);
	         sheet.mergeCells(1,3,5,3);
	         Label label8 = new Label(0, 4, "政治面貌");
	         sheet.addCell(label8);
	         Label label9 = new Label(2, 4, "电话");
	         sheet.addCell(label9);
	         Label label10 = new Label(4, 4, "专业");
	         sheet.addCell(label10);
	         sheet.mergeCells(6,1,7,6);
	         Label label12=new Label(0,7,"个人简介",format2);
	         sheet.mergeCells(0,7,7,7);
	         sheet.addCell(label12);
	         sheet.mergeCells(0,8,7,17);
	         Label label13=new Label(0,18,"专长",format2);
	         sheet.mergeCells(0,18,7,18);
	         sheet.addCell(label13);
	         sheet.mergeCells(0,19,7,30);
//	          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
//	          以及单元格内容为test
//	         Label label = new Label(0, 0, "test");
	
	         // 将定义好的单元格添加到工作表中
	         //sheet.addCell(label);
	
	         
	
	         // 写入数据并关闭文件
	         book.write();
	         book.close();
	
	     } catch (Exception e) {
	         System.out.println(e);
	     }
	}
}
