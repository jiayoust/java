package ���߳�;

import java.util.Scanner;

class Seller implements Runnable{
	private int five, ten,twenty,fifty,hundred;
	 public Seller()
	  {
		  five = 0;
		  ten = 1;
		  twenty = 0;
	  }
 public void run() {		  
 int Flag;
 Flag = 0;
 String strInfo = Thread.currentThread().getName();
 String str[] = strInfo.split(":");//��ȡ�û������Ǯ   Ǯ���û�������һ���
 String ThName = str[0];
 int inMoney = Integer.parseInt(str[1]);
     while (Flag ==0) {
	 synchronized(this){
            	
	if(inMoney == 5)
	{
		this.five++;
		 Flag=1;
		 System.out.println(ThName+"success!");
		 notifyAll();
		            	}
		  if(inMoney == 10)//1��5
		   {
		     if(this.five>0)
		     {
		         this.ten++;
		          this.five--;
		          Flag=1;
			      System.out.println(ThName+"success!");
			      notifyAll();
		       }
		      else{
		           try {
		            	System.out.println(ThName+"wait!");
							super.wait();
							} catch (InterruptedException e)
		           {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		            		}
		            	
		            	}
		     if(inMoney == 20)//1��5 1��10	 //3��5	            		
			 {		       			            	
		        if(this.five>0)
		        {
		        if(this.ten>0){//1��5 1��10
		            this.twenty++;
		            this.five--;
		            this.ten--;
		            Flag=1;
				    System.out.println(ThName+"success!");
				     notifyAll();
		           }
		          else{
		            	if(this.five>2){//3��5
		            		this.twenty++;
		            		this.five -=3;
		            		Flag=1;
					        System.out.println(ThName+"success!");
					        notifyAll();
		            				}
		                else{
		            	try {
				        System.out.println(ThName+"wait!");
						super.wait();
						} catch (InterruptedException e) {
											// TODO Auto-generated catch block
						e.printStackTrace();
										}
		            				}
		            			}
		            		}
		            	else{
		            		try {
		            				System.out.println(ThName+"wait!");
									super.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		            		}
		            		
		            	}
		            	if(inMoney==50) {
		            	//1��5  ��
		            	//2��20
		            	//2��10 1��20		            		
		            	//4��10		            		
		            	//3��5��
		            	//1��10 1��20
		            	//3��10
		            	//5��5��
		            	// 1��20
		            	//2��10
		            	//7��5: 1��10
		            	//9��5
		            	if(this.five>0) {	            			
		            		if(this.twenty>1) {//1��5	2��20
		            		this.fifty++;
		            		this.five--;
		            		this.twenty-=2;	
		            		Flag=1;
				            System.out.println(ThName+"success!");
				            notifyAll();
		            		}
		            	else {
		            		if(this.twenty>0) {
		            			if(this.ten>1) {//1��5 2��10 1��20
		            			this.fifty++;
		            			this.five--;
		            			this.ten-=2;
		            			this.twenty--;
		            			Flag=1;
						        System.out.println(ThName+"success!");
						        notifyAll();
		            			}
		            	else {
		            		if(this.ten>0&&this.five>2) {//3��5 1��10 1��20
		            			this.five-=3;
		            			this.ten--;
		            			this.twenty--;
		            			this.fifty++;
		            			Flag=1;
							   System.out.println(ThName+"success!");
							   notifyAll();
		            			}
		            	else{
		            			if(this.five>4){//5��5 1��20
		            			this.five-=5;
		            			this.twenty--;
		            			this.fifty++;
		            			Flag=1;
								System.out.println(ThName+"success!");
								 notifyAll();
		            			}
		            	else {
		            			try {
		            		        System.out.println(ThName+"wait!");
		            				super.wait();
		            				} catch (InterruptedException e) {
		            									// TODO Auto-generated catch block
		            				e.printStackTrace();
		            			}
		            		}
		            		}
		            }
		     	}
		            	else {
		            			if(this.ten>3) {//1��5 4��10
		            			this.fifty++;
		            			this.five--;
		            			this.ten-=4;
		            			Flag=1;
						     System.out.println(ThName+"success!");
						        notifyAll();
		            			}
		            	else{
		            			if(this.ten>2&&this.five>2) {//3��5 3��10
		            			this.fifty++;
		            			this.five-=3;
		            			this.ten-=3;
		            			Flag=1;
		            			System.out.println(ThName+"success!");
							  	notifyAll();
		            			}
		            	else {
		            			if(this.ten>1&&this.five>4) {//5��5 2��10
		            			this.five-=5;
		            			this.ten-=2;
		            			this.fifty++;
		            			Flag=1;
								System.out.println(ThName+"success!");
								 notifyAll();
		            				}
		            		else{
		            			if(this.ten>0&&this.five>6) {//7��5 1��10
		            				this.fifty++;
		            				this.five-=7;
		            				this.ten--;
		            				Flag=1;
		    						System.out.println(ThName+"success!");
		    						notifyAll();
		            				}
		            		else {
		            				if(this.five>8) {//9��5
		            					this.fifty++;
		            					this.five-=9;				
		            					Flag=1;
		        						System.out.println(ThName+"success!");
		        						notifyAll();
		            				}
		            		else {
		            					try {
				            		        System.out.println(ThName+"wait!");
				            				super.wait();
				            				} catch (InterruptedException e) {
				            									// TODO Auto-generated catch block
				            						e.printStackTrace();
				            			}
		            				}
		            		   }
		            	    }
		     	          }
	                     }
		            	}
	                 }
		        	}
	    	else {
	    			try {
		            	System.out.println(ThName+"wait!");
						super.wait();
						} catch (InterruptedException e) {
									// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 }
     }
 }
}

           if(inMoney==100) {
		            			for(int i=this.fifty;i>-1;i--) {
			            			for(int j=this.twenty;j>-1;j--) {
			            				for(int k=this.ten;k>-1;k--) {
			            					for(int l=0;l<=this.five;l++) {
			            						if(i*50+j*20+k*10+l*5==95) {			            						
													this.fifty-=i;
													this.twenty-=j;
													this.ten-=k;
													this.five-=l;
													this.hundred++;
													Flag=1;
        						            		System.out.println(ThName+"success!");
        						            		notifyAll();
        						            		break;        						            		        						            		
			            						}
			            						
			            					}
			            				}
			            			}
			            		}
		            			if(Flag==0)
{
		            				try {
			            				System.out.println(ThName+"wait!");
										super.wait();
									} 
		            				catch (InterruptedException e) 
		            				{
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		            			}
		            	}	                  	          
	            }
            
	  }
	  

