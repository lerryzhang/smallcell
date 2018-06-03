package com.small.cell.server.pojo;

public class General {

	public static final int Mac = 0x0001; // 设备MAC地址
	public static final int Model = 0x0002;// 设备型号
	public static final int FW = 0x0003; // 固件版本
	public static final int Res = 0x0004; // 结果
	public static final int StartType = 0x0005; // 启动类型
	public static final int REM = 0x0006;// REM扫描状态
	public static final int TDD_D = 0x0007; // 对应型号：TDD D频（ZL-81X3-D）
	public static final int TDD_E = 0x0008;// 对应型号：TDD E频（ZL-81X3-E）
	public static final int TDD_F = 0x0009; // 对应型号：TDD F频 (ZL-81X3-F)
	public static final int FDD_Band1 = 0x000a; // 对应型号：FDD Band1电信 (ZL-81X3-1)
	public static final int FDD_Band1_Unicom = 0x000b; // 对应型号：FDD Band1联通 (保留)
	public static final int FDD_Band3_Telecom = 0x000c; // 对应型号：FDD Band3电信
														// (ZL-81X3-3D)
	public static final int FDD_Band3_Unicom = 0x000d; // 对应型号：FDD Band3联通
														// (ZL-81X3-3L)
	public static final int FDD_All = 0x000e; // 对应所有型号 （全局性配置使用，如服务器IP）
	public static final int RouterFW = 0x000f;// 路由器固件版本
	public static final int ZL_8110 = 0x0010;// 对应型号：TDD E频（室内普通，ZL-8110）
	public static final int ZL_8700_D = 0x0011; // 对应型号：TDD D频（室内加功放，ZL-8700-D）
	public static final int ZL_8700_E = 0x0012;// 对应型号：TDD E频 (室内加功放，ZL-8700-E)
	public static final int ZL_8700_F = 0x0013; // 对应型号：TDD F频 (室内加功放，ZL-8700-F)
	public static final int ZL_8700_1 = 0x0014; // 对应型号：FDD Band1电信
												// (室内加功放，ZL-8700-1)
	public static final int FDD_Band1_Unicom_Default = 0x0015;// ( 0x0015
																// 对应型号：FDD
																// Band1联通 (保留)
	public static final int ZL_8700_3D = 0x0016;// 对应型号：FDD Band3电信
												// (室内加功放，ZL-8700-3D)
	public static final int ZL_8700_3L = 0x0017; // 对应型号：FDD Band3联通
													// (室内加功放，ZL-8700-3L)

}
