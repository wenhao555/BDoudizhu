package com.example.sf.bdoudizhu.Activity;

import java.util.Arrays;

//一组牌的可能类型
enum Type
{
	Unknown (0), //未知
	Single (1), //单张
	Double (2), //对子
	Three (3), //三条
	SingleSeq (4), //单顺
	DoubleSeq (5), //双顺
	ThreeSeq (6), //三顺
	ThreePlus (7), //三带一（一张或一对）
	Airplane (8), //飞机
	FourSeq (9), //四带二（两张或两对）
	Bomb (10); //炸弹、王炸


	private int value;

	Type (int value)
	{
		this.value = value;
	}

	int getValue ()
	{
		return value;
	}
}

//牌型结构
class CardGroup
{
	private int[] group = new int[18]; //3-17权值集合
	private boolean[] cards = new boolean[54]; //0-53组成的集合，主要用于方便画面显示
	private Type type;//卡牌类型（单牌、对子等等）
	private int value;//权值
	private int count;//此结构元素数量（牌数量）

	CardGroup ()
	{
		type = Type.Unknown;
		value = 0;
		count = 0;
	}

	CardGroup (Type type, int value)
	{
		this.type = type;
		this.value = value;
		count = 0;
	}

	void copyFrom (CardGroup cg)
	{
		this.group = Arrays.copyOf (cg.group, cg.group.length);
		this.cards = Arrays.copyOf (cg.cards, cg.cards.length);
		this.type = cg.type;
		this.value = cg.value;
		this.count = cg.count;
	}

	//重置卡牌类型
	void clear ()
	{
		group = new int[18];
		cards = new boolean[54];
		type = Type.Unknown;
		value = 0;
		count = 0;
	}

	//添加0-53表示的牌
	void addCard (int num)
	{
		if (!cards[num])
		{
			++count;
			cards[num] = true;
			++group[translate (num)];
		}
	}

	//将卡牌转换成3-17的权值，其中A为14、2为15、小王是16、大王是17
	static int translate (int num)
	{
		if (num < 52)
			return num / 4 + 3;
		else
			return num - 36;
	}

	void setType (Type type)
	{
		this.type = type;
	}

	Type getType ()
	{
		return type;
	}

	void setValue ()
	{
		++value;
	}

	void setValue (int value)
	{
		this.value = value;
	}

	int getValue ()
	{
		return value;
	}

	int getCount ()
	{
		return count;
	}

	boolean hasCard (int i)
	{
		return i > -1 && i < 54 && cards[i];
	}
}
