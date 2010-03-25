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
