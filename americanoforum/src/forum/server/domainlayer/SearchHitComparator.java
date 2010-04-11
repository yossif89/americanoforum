/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Yossi
 */
class SearchHitComparator implements Comparator{

    /**
     * compares the two hits
     * @param hit1 first hit to compare
     * @param hit2 second hit to compare
     * @return 1 if hit1<hit2, -1 if hit2<hit1 and 0 if hit2=hit1
     */
    public int compare(Object hit1, Object hit2) {
        SearchHit sh1 = (SearchHit)hit1;
        SearchHit sh2 = (SearchHit)hit2;
        if (sh1.getScore() > sh2.getScore())
            return -1;
        if (sh1.getScore() < sh2.getScore())
            return 1;
        Date d1 = sh1.getMessage().getDate();
        Date d2 = sh2.getMessage().getDate();
        if (d1.after(d2))
            return -1;
        if (d1.before(d2))
            return 1;
        return 0;
    }

}
