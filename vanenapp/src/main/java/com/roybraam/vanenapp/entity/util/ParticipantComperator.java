package com.roybraam.vanenapp.entity.util;

import com.roybraam.vanenapp.entity.Participant;

/**
 * Created by Roy Braam on 30-7-15.
 * Compares participants with poules.
 * Participants without poules are set as last
 */
public class ParticipantComperator implements java.util.Comparator<Participant> {
    @Override
    public int compare(Participant p1, Participant p2) {
        if (p1 == null){
            return -1;
        }if (p2 == null){
            return 1;
        }

        if (p1.getPoule() == null){
            return 1;
        }if (p2.getPoule() == null){
            return -1;
        }
        if (p1.getPoule().getId() == p2.getPoule().getId()){
            return 0;
        }
        int compare = 0;
        if (p1.getPoule().getType() == null){
            return 1;
        }
        compare = p1.getPoule().getType().compareTo(p2.getPoule().getType());
        if (compare != 0) {
            return compare;
        }

        if (p2.getPoule().getStartKyu() == null){
            return 1;
        }
        compare = p1.getPoule().getStartKyu().compareTo(p2.getPoule().getStartKyu());
        if (compare != 0){
            return compare;
        }

        if (p1.getPoule().getStartAge() == null){
            return 1;
        }
        compare =  p1.getPoule().getStartAge().compareTo(p2.getPoule().getStartAge());
        if (compare != 0){
            return compare;
        }

        if (p1.getPoule().getEndKyu() == null){
            return -1;
        }
        compare = p1.getPoule().getEndKyu().compareTo(p2.getPoule().getEndKyu());
        if (compare != 0){
            return compare;
        }

        if (p1.getPoule().getEndAge() == null){
            return -1;
        }
        compare = p1.getPoule().getEndAge().compareTo(p2.getPoule().getEndAge());
        if (compare != 0){
            return compare;
        }

        return compare;
    }
}
