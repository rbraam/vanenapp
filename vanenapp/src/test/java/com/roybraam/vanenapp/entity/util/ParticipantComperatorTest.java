package com.roybraam.vanenapp.entity.util;


import com.roybraam.vanenapp.entity.CompetitionType;
import com.roybraam.vanenapp.entity.Kyu;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParticipantComperatorTest {

    public ParticipantComperator comp = new ParticipantComperator();

    private Participant participant1;
    private Participant participant2;
    private Poule p1;
    private Poule p2;
    @Before
    public void setUp() throws Exception {
        p1 = new Poule();
        p1.setId(1);
        p1.setName("1");
        p1.setStartKyu(Kyu.KYU_8);
        p1.setEndKyu(Kyu.KYU_8);
        p1.setGender("MALE");
        p1.setStartAge(1);
        p1.setEndAge(2);
        p1.setType(CompetitionType.KATA);

        p2 = new Poule(p1);

        participant1 = new Participant();
        participant1.setPoule(p1);
        participant2 = new Participant();
        participant2.setPoule(p2);
    }

    @Test
    public void withNullTest(){
        assertEquals(-1, comp.compare(null, null));
        assertEquals(1, comp.compare(new Participant(), null));
    }

    @Test
    public void testEqual() throws Exception {
        assertEquals(0,comp.compare(participant1, participant2));

        p2.setId(2);
        assertEquals(0,comp.compare(participant1, participant2));
    }

    @Test
    public void testEndAgeDif(){
        p2.setEndAge(3);
        p2.setId(2);
        assertEquals(-1, comp.compare(participant1, participant2));
    }

    @Test
    public void testEndKyuDif(){
        p2.setEndKyu(Kyu.KYU_7);
        p2.setId(2);
        assertEquals(-1,comp.compare(participant1, participant2));
    }
}