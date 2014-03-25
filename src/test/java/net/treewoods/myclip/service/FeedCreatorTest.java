/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.treewoods.myclip.service;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author toru
 */
public class FeedCreatorTest {
	
	public FeedCreatorTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of setFeedType method, of class FeedCreator.
	 */
	@Test
	public void testSetFeedType() {
        FeedCreator feed = new FeedCreator("rss_2.0");
        feed.setFeedTitle("WEB上のボクについて");
        feed.setFeedLink("http://www.geocities.jp/kashi57move/");
        feed.setFeedDescription("ボクがWEB上で展開しているサイトやブログの情報です。");
  
        feed.setTextEntry(
                "技術への名残り"
                , "http://kashi57move.blogspot.com/"
                , "IT技術やマーケティング関連を中心に思いつくままに書いています。"
                , new Date());
  
        feed.setTextEntry(
                "ぼや記"
                , "http://blog.livedoor.jp/kashi57move/"
                , "くだらない日常を出来るだけくだらなく書き留めているつまらない日記です"
                , new Date());
  
        feed.setTextEntry(
                "kashi57move web"
                , "http://www.geocities.jp/kashi57move/"
                , "昔ながらの個人的HP。日記がメインでしたがブログに移行したので、ここ数年更新していません。"
                , new Date());
  
        feed.setTextEntry(
                "BOLDWEB"
                , "http://www.boldweb.jp/"
                , "仕事用のHPですが、まだまったく手を付けられていません…。"
                , new Date());
  
        feed.createFeed();
	}

	/**
	 * Test of setFeedTitle method, of class FeedCreator.
	 */
	@Test
	public void testSetFeedTitle() {
		System.out.println("setFeedTitle");

	}

	/**
	 * Test of setFeedLink method, of class FeedCreator.
	 */
	@Test
	public void testSetFeedLink() {
		System.out.println("setFeedLink");

	}

	/**
	 * Test of setFeedDescription method, of class FeedCreator.
	 */
	@Test
	public void testSetFeedDescription() {
		System.out.println("setFeedDescription");

	}

	/**
	 * Test of setTextEntry method, of class FeedCreator.
	 */
	@Test
	public void testSetTextEntry() {
		System.out.println("setTextEntry");

	}

	/**
	 * Test of createFeed method, of class FeedCreator.
	 */
	@Test
	public void testCreateFeed() {
		System.out.println("createFeed");

	}
	
}
