package com.lpmas.sfhn.portal.declare.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;

public class FormInstructionConfig {

	// 填表说明配置
	public static final String FORM_INSTRUCTION_YOUNG_FARMER = "1.现代青年农场主计划对象申报表由有意愿参加现代青年农场主计划的人员按个人实际情况填写。<br>"
			+ "2.中专、大专、大学及以上文化程度请填写专业。<br>" + "3.获取证书情况可多选。<br>" + "4.专业学习培训经历可在空白处添加相关内容，字数不超过300字。<br>"
			+ "5.主体产业可选择1-3项填写，产业规模和年限对应所选主体产业分别填写。<br>" + "6.上年度产业收入和上年度家庭收入，初次填写为认定年度上一年的收入，之后填写为审核年度上一年的收入。";
	public static final String FORM_INSTRUCTION_PRODUCT_FARMER = "1.生产经营型职业农民对象申报表由有意愿参加生产经营型职业农民培育的人员按个人实际情况填写。<br>"
			+ "2.中专、大专、大学及以上文化程度请填写专业。<br>" + "3. 获取证书情况可多选。<br>" + "4.专业学习培训经历可在空白处添加相关内容，字数不超过300字。<br>"
			+ "5.主体产业可选择1-3项填写，产业规模和年限对应所选主体产业分别填写。<br>" + "6.上年度产业收入和上年度家庭收入，初次填写为认定年度上一年的收入，之后填写为审核年度上一年的收入。";
	public static final String FORM_INSTRUCTION_TECHNICAL_FARMER = "1. 专业技能型、专业服务型职业农民培育对象申报表由有意愿参加专业技能型或专业服务型职业农民培育人员按个人实际情况填写。<br>"
			+ "2. 个人从事该工种年收入为统计年度的上一年收入。<br>" + "3. 专业学习培训经历可在空白处添加相关内容，字数不超过300字。<br>" + "4. 专业技能型职业农民从事工种分类按下列种类进行填写。<br>"
			+ "（1）农艺工：粮食作物栽培工、棉花作物栽培工、油料作物栽培工、糖料作物栽培工、麻、烟类作物栽培工、啤酒花栽培工、牧草栽培工、其他农艺工；<br>"
			+ "（2）园艺工：蔬菜园艺工、菌类园艺工、果树园艺工、花卉园艺工、茶园园艺工、蚕桑园艺工、其他园艺工；<br>" + "（3）牧草工：牧草种子繁育工、牧草种子检验工、牧草栽培工、牧草产品加工工、其他牧草工；<br>"
			+ "（4）热带作物生产工：橡胶育苗工、橡胶栽培工、橡胶割胶工、橡胶制胶工、其他天然橡胶生产工、剑麻栽培工、剑麻制品工、剑麻纤维生产工、热带作物初制工；<br>" + "（5）家畜繁殖员：家畜繁殖员；<br>"
			+ "（6）家畜饲养员：牛羊饲养员、生猪饲养员、其他家畜饲养员；<br>" + "（7）家禽繁殖员：家禽繁殖员；<br>" + "（8）家禽饲养员：鸡的饲养员、水禽饲养员、其他家禽饲养员；<br>"
			+ "（9）特种动物饲养员：特种禽类饲养员、特种经济动物繁育员、药用动物养殖员、蜜蜂饲养员，其他特种动物饲养员；<br>" + "（10）实验动物养殖员：实验动物养殖员；<br>"
			+ "（11）渔业生产船员：海洋普通渔业船员、内陆渔业船员、渔船驾驶人员、渔船电机员、渔船无线电操作员、渔船机驾长、渔船轮机人员；<br>"
			+ "（12）水生动物苗种繁育工：淡水鱼苗种繁育工、淡水虾、蟹、贝类苗种繁育工、海水鱼苗种繁育工、海水虾、蟹、贝类苗种繁育工、珍稀水生动物苗种繁育工、其他水生动物苗种繁育工；<br>"
			+ "（13）水生植物苗种培育工：海藻育苗工、淡水水生植物苗种培育工、其他水生植物苗种培育工；<br>"
			+ "（14）水生动物饲养工：淡水成鱼饲养工、淡水虾、蟹、贝类饲养工、海水成鱼饲养工、海水虾、蟹、贝类饲养工、珍稀水生动物饲养工、其他水生动物饲养工；<br>" + "（15）水生植物栽培工：水生植物栽培工；<br>"
			+ "（16）珍珠养殖工：淡水育珠工、海水育珠工；<br>" + "（17）水产捕捞工：淡水捕捞工、海水捕捞工、水生动植物采集工；<br>" + "（18）其他：农产品贮藏加工人员、其他人员。";
	public static final String FORM_INSTRUCTION_SERVICE_FARMERO = "1. 专业技能型、专业服务型职业农民培育对象申报表由有意愿参加专业技能型或专业服务型职业农民培育人员按个人实际情况填写。<br>"
			+ "2. 个人从事该工种年收入为统计年度的上一年收入。<br>" + "3. 专业学习培训经历可在空白处添加相关内容，字数不超过300字。<br>" + "4. 专业服务型职业农民从事岗位分类按下列种类进行填写。<br>"
			+ "（1）种植服务：肥料配方员、种子经销员、农药经销员、农作物植保员、农作物种子繁育员、种苗繁育员、其他种植服务人员；<br>"
			+ "（2）畜牧服务：村级动物防疫员、兽药经销员、饲料检验化验员、动物检疫检验员、其他畜牧服务人员；<br>"
			+ "（3）渔业服务：水生植物病害防治员、水生动物病害防治员、水生植物疫病检疫员、水生动物检疫防疫员、水产技术指导员、其他渔业服务人员；<br>"
			+ "（4）农业机械服务：农业机械操作人员、农业机械维修人员、农机营销员、农机技术指导员、农机服务经纪人、其他农业机械服务人员；<br>"
			+ "（5）其他：农村经纪人、农村信息员、村级资产管理员、村级奶站管理员、农村土地承包仲裁员、测土配方施肥员、沼气生产工、沼气物管员、农村传统手工业人员、休闲农业服务员、农产品检测员、农村环境保护工、农村节能员、太阳能利用工、微水电利用工、小风电利用工、其他人员。";
	public static final String FORM_INSTRUCTION_LEADER_FARMER = "1.对象申报表由有意愿参加现代农业经营主体带头人轮训计划的人员按个人实际情况填写。<br>"
			+ "2. 中专、大专、大学及以上文化程度请填写专业。<br>" + "3. 获取证书情况可多选。<br>" + "4. 专业学习培训经历可在空白处添加相关内容，字数不超过300字。<br>"
			+ "5. 主体产业可选择1-3项填写，产业规模和年限对应所选主体产业分别填写。<br>" + "6. 上年度产业收入和上年度家庭收入，初次填写为认定年度上一年的收入，之后填写为审核年度上一年的收入。";
	public static List<StatusBean<Integer, String>> FORM_INSTRUCTION_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> FORM_INSTRUCTION_MAP = new HashMap<Integer, String>();
	
	static{
		initFormInstructionList();
		initFormInstructionMap();
	}

	private static void initFormInstructionList() {
		FORM_INSTRUCTION_LIST = new ArrayList<StatusBean<Integer, String>>();
		FORM_INSTRUCTION_LIST.add(new StatusBean<Integer, String>(DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER,
				FORM_INSTRUCTION_YOUNG_FARMER));
		FORM_INSTRUCTION_LIST.add(new StatusBean<Integer, String>(DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER,
				FORM_INSTRUCTION_PRODUCT_FARMER));
		FORM_INSTRUCTION_LIST.add(new StatusBean<Integer, String>(DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER,
				FORM_INSTRUCTION_TECHNICAL_FARMER));
		FORM_INSTRUCTION_LIST.add(new StatusBean<Integer, String>(DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER,
				FORM_INSTRUCTION_SERVICE_FARMERO));
		FORM_INSTRUCTION_LIST.add(new StatusBean<Integer, String>(DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER,
				FORM_INSTRUCTION_LEADER_FARMER));
	}

	private static void initFormInstructionMap() {
		FORM_INSTRUCTION_MAP = StatusKit.toMap(FORM_INSTRUCTION_LIST);
	}

}
