//����Excel����
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
	         // ���ļ�
	         WritableWorkbook book = Workbook.createWorkbook(new File("test.xls"));
	         // ������Ϊ����һҳ���Ĺ���������0��ʾ���ǵ�һҳ
	         WritableSheet sheet = book.createSheet("��һҳ", 0);
	         WritableFont font1 = new WritableFont(WritableFont.ARIAL,17);
	         WritableCellFormat format1=new WritableCellFormat(font1); 
	         WritableCellFormat format2=new WritableCellFormat();
	         format1.setAlignment(jxl.format.Alignment.CENTRE);
	         format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	         format2.setAlignment(jxl.format.Alignment.CENTRE);
	         
	         Label label=new Label(0,0,"��ְ����",format1);
	         sheet.mergeCells(0,0,7,0);
	         sheet.setRowView(0,500);
	         sheet.addCell(label);
	         Label label1 = new Label(0, 1, "����");
	         sheet.addCell(label1);
	         Label label2 = new Label(2, 1, "�Ա�");
	         sheet.addCell(label2);
	         Label label3 = new Label(4, 1, "����");
	         sheet.addCell(label3);
	         Label label4 = new Label(0, 2, "��������");
	         sheet.addCell(label4);
	         Label label5 = new Label(2, 2, "����");
	         sheet.addCell(label5);
	         Label label6 = new Label(4, 2, "����");
	         sheet.addCell(label6);
	         Label label7 = new Label(0, 3, "��ͥ��ַ");
	         sheet.addCell(label7);
	         sheet.mergeCells(1,3,5,3);
	         Label label8 = new Label(0, 4, "������ò");
	         sheet.addCell(label8);
	         Label label9 = new Label(2, 4, "�绰");
	         sheet.addCell(label9);
	         Label label10 = new Label(4, 4, "רҵ");
	         sheet.addCell(label10);
	         sheet.mergeCells(6,1,7,6);
	         Label label12=new Label(0,7,"���˼��",format2);
	         sheet.mergeCells(0,7,7,7);
	         sheet.addCell(label12);
	         sheet.mergeCells(0,8,7,17);
	         Label label13=new Label(0,18,"ר��",format2);
	         sheet.mergeCells(0,18,7,18);
	         sheet.addCell(label13);
	         sheet.mergeCells(0,19,7,30);
//	          ��Label����Ĺ�������ָ����Ԫ��λ���ǵ�һ�е�һ��(0,0)
//	          �Լ���Ԫ������Ϊtest
//	         Label label = new Label(0, 0, "test");
	
	         // ������õĵ�Ԫ����ӵ���������
	         //sheet.addCell(label);
	
	         
	
	         // д�����ݲ��ر��ļ�
	         book.write();
	         book.close();
	
	     } catch (Exception e) {
	         System.out.println(e);
	     }
	}
}
