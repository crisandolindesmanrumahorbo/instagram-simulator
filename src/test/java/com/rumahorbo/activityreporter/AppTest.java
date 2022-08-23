package com.rumahorbo.activityreporter;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void follows() {
        Followable alice = new User("Alice");
        User bob = new User("Bob");

        alice.follows(bob);

        assertTrue(alice.isFollow(bob));
    }

    @Test
    public void upload() {
        User alice = new User("Alice");
        User bob = new User("Bob");

        bob.follows(alice);
        alice.uploadPhoto();

        assertEquals(1, bob.activityLog().size());
        assertEquals("Alice uploaded photo\n", bob.activityLogString());
    }

    @Test
    public void likes() {
        Likeable alice = new User("Alice");
        User bob = new User("Bob");

        alice.likePhoto(bob);

        assertEquals(1, bob.activityLog().size());
        assertEquals("Alice liked your photo\n", bob.activityLogString());
    }

    @Test
    public void integration_test() {
        User alice = new User("Alice");
        User bob = new User("Bob");
        User john = new User("John");
        User bill = new User("Bill");

        alice.follows(bob);
        john.follows(bob);
        bob.follows(alice);
        bob.follows(bill);
        john.follows(alice);

        alice.uploadPhoto();
        bob.likePhoto(alice);
        bill.uploadPhoto();
        bob.likePhoto(bill);

        System.out.println("Bob");
        System.out.println(bob.activityLogString());

        System.out.println("John");
        System.out.println(john.activityLogString());

        System.out.println("Alice");
        System.out.println(alice.activityLogString());

        System.out.println("Bill");
        System.out.println(bill.activityLogString());

        assertEquals(4, bob.activityLog().size());
        assertEquals(3, john.activityLog().size());
        assertEquals(3, alice.activityLog().size());
        assertEquals(2, bill.activityLog().size());
    }
}
