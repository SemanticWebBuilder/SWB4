/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.semanticwb.model.Traceable;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.VideoIn;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialComparator implements Comparator {

    /**
     * The lang.
     */
    String lang = null;
    SemanticProperty semProp = null;

    /**
     * Creates new WBPriorityComaprator.
     */
    public SWBSocialComparator() {
    }

    /**
     * Instantiates a new sWB comparator.
     *
     * @param lang the lang
     */
    public SWBSocialComparator(String lang) {
        this.lang = lang;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(java.lang.Object obj1, java.lang.Object obj2) {
        return 0;
    }

    /**
     * Sort by sortByPostType name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByPostType(Iterator it, boolean ascendent) {

        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(ascendent)
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1 = null;
                    Date d2 = null;
                    if (o1 instanceof MessageIn && o2 instanceof PhotoIn) {
                        return 1;
                    } else if (o1 instanceof MessageIn && o2 instanceof VideoIn) {
                        return 1;
                    } else if (o1 instanceof MessageIn && o2 instanceof MessageIn) {
                        MessageIn msgIn1 = (MessageIn) o1;
                        d1 = msgIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        MessageIn msgIn2 = (MessageIn) o2;
                        d2 = msgIn2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    } else if (o1 instanceof PhotoIn && o2 instanceof MessageIn) {
                        return -1;
                    } else if (o1 instanceof PhotoIn && o2 instanceof VideoIn) {
                        return 1;
                    } else if (o1 instanceof PhotoIn && o2 instanceof PhotoIn) {
                        PhotoIn photo1 = (PhotoIn) o1;
                        d1 = photo1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        PhotoIn photo2 = (PhotoIn) o2;
                        d2 = photo2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    } else if (o1 instanceof VideoIn && o2 instanceof MessageIn) {
                        return -1;
                    } else if (o1 instanceof VideoIn && o2 instanceof PhotoIn) {
                        return -1;
                    } else if (o1 instanceof VideoIn && o2 instanceof VideoIn) {
                        VideoIn video1 = (VideoIn) o1;
                        d1 = video1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        VideoIn video2 = (VideoIn) o2;
                        d2 = video2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    }

                    if (d1 == null && d2 != null) {
                        return -1;
                    }
                    if (d1 != null && d2 == null) {
                        return 1;
                    }
                    if (d1 == null && d2 == null) {
                        return -1;
                    } else {
                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                        return ret;
                    }
                }
            });
            }else{  //TODO:Revisar si funciona este ordenamiento, no lo revise porque aun no tenia mensajes de tipo foto, ni video.
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1 = null;
                    Date d2 = null;
                    if (o1 instanceof MessageIn && o2 instanceof PhotoIn) {
                        return -1;
                    } else if (o1 instanceof MessageIn && o2 instanceof VideoIn) {
                        return -1;
                    } else if (o1 instanceof MessageIn && o2 instanceof MessageIn) {
                        MessageIn msgIn1 = (MessageIn) o1;
                        d1 = msgIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        MessageIn msgIn2 = (MessageIn) o2;
                        d2 = msgIn2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    } else if (o1 instanceof PhotoIn && o2 instanceof MessageIn) {
                        return 1;
                    } else if (o1 instanceof PhotoIn && o2 instanceof VideoIn) {
                        return -1;
                    } else if (o1 instanceof PhotoIn && o2 instanceof PhotoIn) {
                        PhotoIn photo1 = (PhotoIn) o1;
                        d1 = photo1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        PhotoIn photo2 = (PhotoIn) o2;
                        d2 = photo2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    } else if (o1 instanceof VideoIn && o2 instanceof MessageIn) {
                        return 1;
                    } else if (o1 instanceof VideoIn && o2 instanceof PhotoIn) {
                        return 1;
                    } else if (o1 instanceof VideoIn && o2 instanceof VideoIn) {
                        VideoIn video1 = (VideoIn) o1;
                        d1 = video1.getSemanticObject().getDateProperty(Traceable.swb_created);
                        VideoIn video2 = (VideoIn) o2;
                        d2 = video2.getSemanticObject().getDateProperty(Traceable.swb_created);
                    }

                    if (d1 == null && d2 != null) {
                        return 1;
                    }
                    if (d1 != null && d2 == null) {
                        return -1;
                    }
                    if (d1 == null && d2 == null) {
                        return 1;
                    } else {
                        int ret = d1.getTime() > d2.getTime() ? -1 : 1;
                        return ret;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Sort by sortByNetwork name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByNetwork(Iterator it, boolean ascendent) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(ascendent)
            {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn postIn1 = (PostIn) o1;
                        PostIn postIn2 = (PostIn) o2;
                        if (postIn1.getPostInSocialNetwork() == null && postIn2.getPostInSocialNetwork() != null) {
                            return -1;
                        }
                        if (postIn1.getPostInSocialNetwork() != null && postIn2.getPostInSocialNetwork() == null) {
                            return 1;
                        }
                        if (postIn1.getPostInSocialNetwork() == null && postIn2.getPostInSocialNetwork() == null) {
                            return -1;
                        } else {
                            String name1 = postIn1.getPostInSocialNetwork().getTitle();
                            String name2 = postIn2.getPostInSocialNetwork().getTitle();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }else{
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn postIn1 = (PostIn) o1;
                        PostIn postIn2 = (PostIn) o2;
                        if (postIn2.getPostInSocialNetwork() == null && postIn1.getPostInSocialNetwork() != null) {
                            return -1;
                        }
                        if (postIn2.getPostInSocialNetwork() != null && postIn1.getPostInSocialNetwork() == null) {
                            return 1;
                        }
                        if (postIn2.getPostInSocialNetwork() == null && postIn1.getPostInSocialNetwork() == null) {
                            return -1;
                        } else {
                            String name1 = postIn2.getPostInSocialNetwork().getTitle();
                            String name2 = postIn1.getPostInSocialNetwork().getTitle();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Sort by sortByTopic name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByTopic(Iterator it, boolean ascendent) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(ascendent)
            {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof Post && o2 instanceof Post) {
                        Post post1 = (Post) o1;
                        Post post2 = (Post) o2;
                        if (post1.getSocialTopic() == null && post2.getSocialTopic() != null) {
                            return -1;
                        }
                        if (post1.getSocialTopic() != null && post2.getSocialTopic() == null) {
                            return 1;
                        }
                        if (post1.getSocialTopic() == null && post2.getSocialTopic() == null) {
                            return -1;
                        } else {
                            String name1 = post1.getSocialTopic().getTitle();
                            String name2 = post2.getSocialTopic().getTitle();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }else
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof Post && o2 instanceof Post) {
                        Post post1 = (Post) o1;
                        Post post2 = (Post) o2;
                        if (post2.getSocialTopic() == null && post1.getSocialTopic() != null) {
                            return -1;
                        }
                        if (post2.getSocialTopic() != null && post1.getSocialTopic() == null) {
                            return 1;
                        }
                        if (post2.getSocialTopic() == null && post1.getSocialTopic() == null) {
                            return -1;
                        } else {
                            String name1 = post2.getSocialTopic().getTitle();
                            String name2 = post1.getSocialTopic().getTitle();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Sort by sortBySentiment name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortBySentiment(Iterator it, boolean firstPositive) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (firstPositive) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Post && o2 instanceof Post) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int sentimentValuePost1 = postIn1.getPostSentimentalType();
                            int sentimentValuePost2 = postIn2.getPostSentimentalType();

                            if (sentimentValuePost1 == 1 && (sentimentValuePost2 == 0 || sentimentValuePost2 == 2)) {
                                return 1;
                            }
                            if (sentimentValuePost1 == 0 && (sentimentValuePost2 == 2)) {
                                return 1;
                            } else if (sentimentValuePost1 == sentimentValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Post && o2 instanceof Post) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int sentimentValuePost1 = postIn1.getPostSentimentalType();
                            int sentimentValuePost2 = postIn2.getPostSentimentalType();

                            if (sentimentValuePost1 == 2 && (sentimentValuePost2 == 0 || sentimentValuePost2 == 1)) {
                                return 1;
                            }
                            if (sentimentValuePost1 == 0 && (sentimentValuePost2 == 1)) {
                                return 1;
                            } else if (sentimentValuePost1 == sentimentValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Sort by sortByIntensity name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByIntensity(Iterator it, boolean firstHigh) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (firstHigh) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int intensityValuePost1 = postIn1.getPostIntesityType();
                            int intensityValuePost2 = postIn2.getPostIntesityType();

                            if (intensityValuePost1 > intensityValuePost2) {
                                return -1;
                            } else if (intensityValuePost1 == intensityValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return 1;
                            }
                        } else {
                            return 1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Post && o2 instanceof Post) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int intensityValuePost1 = postIn1.getPostIntesityType();
                            int intensityValuePost2 = postIn2.getPostIntesityType();

                            if (intensityValuePost1 < intensityValuePost2) {
                                return -1;
                            } else if (intensityValuePost1 == intensityValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return 1;
                            }
                        } else {
                            return 1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    
    /**
     * Sort by sortBySentiment name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByEmoticon(Iterator it, boolean firstPositive) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (firstPositive) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Post && o2 instanceof Post) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int emoticonValuePost1 = postIn1.getPostSentimentalEmoticonType();
                            int emoticonValuePost2 = postIn2.getPostSentimentalEmoticonType();

                            if (emoticonValuePost1 == 1 && (emoticonValuePost2 == 0 || emoticonValuePost2 == 2)) {
                                return 1;
                            }
                            if (emoticonValuePost1 == 0 && (emoticonValuePost2 == 2)) {
                                return 1;
                            } else if (emoticonValuePost1 == emoticonValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof Post && o2 instanceof Post) {
                            PostIn postIn1 = (PostIn) o1;
                            PostIn postIn2 = (PostIn) o2;

                            int emoticonValuePost1 = postIn1.getPostSentimentalEmoticonType();
                            int emoticonValuePost2 = postIn2.getPostSentimentalEmoticonType();

                            if (emoticonValuePost1 == 2 && (emoticonValuePost2 == 0 || emoticonValuePost2 == 1)) {
                                return 1;
                            }
                            if (emoticonValuePost1 == 0 && (emoticonValuePost2 == 1)) {
                                return 1;
                            } else if (emoticonValuePost1 == emoticonValuePost2) {
                                Date d1 = postIn1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = postIn2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }

                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    

    /**
     * Sort by sortByUser name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByUser(Iterator it, boolean ascendent) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(ascendent)
            {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn post1 = (PostIn) o1;
                        PostIn post2 = (PostIn) o2;
                        if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() != null) {
                            return -1;
                        }
                        if (post1.getPostInSocialNetworkUser() != null && post2.getPostInSocialNetworkUser() == null) {
                            return 1;
                        }
                        if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() == null) {
                            return -1;
                        } else {
                            String name1 = post1.getPostInSocialNetworkUser().getSnu_name();
                            String name2 = post2.getPostInSocialNetworkUser().getSnu_name();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }else
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn post1 = (PostIn) o1;
                        PostIn post2 = (PostIn) o2;
                        if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() != null) {
                            return -1;
                        }
                        if (post2.getPostInSocialNetworkUser() != null && post1.getPostInSocialNetworkUser() == null) {
                            return 1;
                        }
                        if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() == null) {
                            return -1;
                        } else {
                            String name1 = post2.getPostInSocialNetworkUser().getSnu_name();
                            String name2 = post1.getPostInSocialNetworkUser().getSnu_name();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Sort by sortByFollowers name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByFollowers(Iterator it, boolean ascendente) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (!ascendente) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post1.getPostInSocialNetworkUser() != null && post2.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int followers1 = post1.getPostInSocialNetworkUser().getFollowers();
                                int followers2 = post2.getPostInSocialNetworkUser().getFollowers();

                                if(followers1>followers2) return 1;
                                if(followers1==followers2)
                                {
                                    Date d1 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post2.getPostInSocialNetworkUser() != null && post1.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int followers1 = post2.getPostInSocialNetworkUser().getFollowers();
                                int followers2 = post1.getPostInSocialNetworkUser().getFollowers();

                                if(followers1>followers2) return 1;
                                if(followers1==followers2)
                                {
                                    Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    
    /**
     * Sort by sortByReplies name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByReplies(Iterator it, boolean ascendente) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (!ascendente) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            
                            int replies1 = post1.getPostRetweets();
                            int replies2 = post2.getPostRetweets();

                            if(replies1>replies2) return 1;
                            if(replies1==replies2)
                            {
                                Date d1 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }
                            }
                            else return -1;
                          
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                           
                            int replies1 = post2.getPostRetweets();
                            int replies2 = post1.getPostRetweets();

                            if(replies1>replies2) return 1;
                            if(replies1==replies2)
                            {
                                Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                                Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                                if (d1 == null && d2 != null) {
                                    return -1;
                                }
                                if (d1 != null && d2 == null) {
                                    return 1;
                                }
                                if (d1 == null && d2 == null) {
                                    return -1;
                                } else {
                                    int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                    return ret;
                                }
                            }
                            else return -1;
                            
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    
    /**
     * Sort by sortByFriends name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByFriends(Iterator it, boolean ascendente) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (!ascendente) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post1.getPostInSocialNetworkUser() != null && post2.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int friends1 = post1.getPostInSocialNetworkUser().getFriends();
                                int friends2 = post2.getPostInSocialNetworkUser().getFriends();

                                if(friends1>friends2) return 1;
                                if(friends1==friends2)
                                {
                                    Date d1 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post2.getPostInSocialNetworkUser() != null && post1.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int friends1 = post2.getPostInSocialNetworkUser().getFriends();
                                int friends2 = post1.getPostInSocialNetworkUser().getFriends();

                                if(friends1>friends2) return 1;
                                if(friends1==friends2)
                                {
                                    Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    
    /**
     * Sort by sortByFriends name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByKlout(Iterator it, boolean ascendente) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if (!ascendente) {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post1.getPostInSocialNetworkUser() != null && post2.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post1.getPostInSocialNetworkUser() == null && post2.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int klout1 = post1.getPostInSocialNetworkUser().getSnu_klout();
                                int klout2 = post2.getPostInSocialNetworkUser().getSnu_klout();

                                if(klout1>klout2) return 1;
                                else if(klout1==klout2)
                                {
                                    Date d1 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            } else {
                set = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2) {
                        if (o1 instanceof PostIn && o2 instanceof PostIn) {
                            PostIn post1 = (PostIn) o1;
                            PostIn post2 = (PostIn) o2;
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() != null) {
                                return -1;
                            }
                            if (post2.getPostInSocialNetworkUser() != null && post1.getPostInSocialNetworkUser() == null) {
                                return 1;
                            }
                            if (post2.getPostInSocialNetworkUser() == null && post1.getPostInSocialNetworkUser() == null) {
                                return -1;
                            } else {
                                int klout1 = post2.getPostInSocialNetworkUser().getSnu_klout();
                                int klout2 = post1.getPostInSocialNetworkUser().getSnu_klout();

                                if(klout1>klout2) return 1;
                                if(klout1==klout2)
                                {
                                    Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                                    Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                                    if (d1 == null && d2 != null) {
                                        return -1;
                                    }
                                    if (d1 != null && d2 == null) {
                                        return 1;
                                    }
                                    if (d1 == null && d2 == null) {
                                        return -1;
                                    } else {
                                        int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                        return ret;
                                    }
                                }
                                else return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    /**
     * Sort by sortByPlace name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByPlace(Iterator it, boolean ascendent) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(ascendent)
            {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn post1 = (PostIn) o1;
                        PostIn post2 = (PostIn) o2;
                        if (post1.getPostPlace() == null && post2.getPostPlace() != null) {
                            return -1;
                        }
                        if (post1.getPostPlace() != null && post2.getPostPlace() == null) {
                            return 1;
                        }
                        if (post1.getPostPlace() == null && post2.getPostPlace() == null) {
                            return -1;
                        } else {
                            String name1 = post1.getPostPlace();
                            String name2 = post2.getPostPlace();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }else
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof PostIn && o2 instanceof PostIn) {
                        PostIn post1 = (PostIn) o1;
                        PostIn post2 = (PostIn) o2;
                        if (post2.getPostPlace() == null && post1.getPostPlace() != null) {
                            return -1;
                        }
                        if (post2.getPostPlace() != null && post1.getPostPlace() == null) {
                            return 1;
                        }
                        if (post2.getPostPlace() == null && post1.getPostPlace() == null) {
                            return -1;
                        } else {
                            String name1 = post2.getPostPlace();
                            String name2 = post1.getPostPlace();

                            int ret;
                            if ((name1 != null) && (name2 != null)) {
                                ret = name1.compareToIgnoreCase(name2);

                                if (ret == 0) {
                                    ret = -1;
                                }
                            } else {
                                ret = -1;
                            }
                            return ret;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    
    /**
     * Sort by sortByPrioritary name set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByPrioritary(Iterator it, boolean priorityHigh) {
        TreeSet set = null;
        try {
            if (it == null) {
                return null;
            }
            if(priorityHigh)
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof Post && o2 instanceof Post) {
                        Post post1 = (Post) o1;
                        Post post2 = (Post) o2;
                        
                        boolean prioritary1 = post1.isIsPrioritary();
                        boolean prioritary2 = post2.isIsPrioritary();

                        if(prioritary1 && !prioritary2) return -1;
                        else if(prioritary1 && prioritary2) 
                        {
                            Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                            Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                            if (d1 == null && d2 != null) {
                                return -1;
                            }
                            if (d1 != null && d2 == null) {
                                return 1;
                            }
                            if (d1 == null && d2 == null) {
                                return -1;
                            } else {
                                int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                return ret;
                            }
                        }else return 1;                           
                        
                    } else {
                        return -1;
                    }
                }
            });
            }else
            {
                set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (o1 instanceof Post && o2 instanceof Post) {
                        Post post1 = (Post) o1;
                        Post post2 = (Post) o2;
                        
                        boolean prioritary1 = post1.isIsPrioritary();
                        boolean prioritary2 = post2.isIsPrioritary();

                        if(prioritary1 && !prioritary2) return 1;
                        else if(prioritary1 && prioritary2) 
                        {
                            Date d1 = post2.getSemanticObject().getDateProperty(Traceable.swb_created);
                            Date d2 = post1.getSemanticObject().getDateProperty(Traceable.swb_created);

                            if (d1 == null && d2 != null) {
                                return -1;
                            }
                            if (d1 != null && d2 == null) {
                                return 1;
                            }
                            if (d1 == null && d2 == null) {
                                return -1;
                            } else {
                                int ret = d1.getTime() > d2.getTime() ? 1 : -1;
                                return ret;
                            }
                        }else return -1;                           
                        
                    } else {
                        return 1;
                    }
                }
            });
            }

            while (it.hasNext()) {
                set.add(it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
}
