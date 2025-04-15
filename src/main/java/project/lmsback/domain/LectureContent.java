package project.lmsback.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lecture_content")
@ToString
public class LectureContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_management_id")
    private Integer lectureManagementId;

    @Column(name = "youtube_video_id")
    private String youtubeVideoId;

    @Column(name = "lecture_call_url")
    private String lectureCallUrl;

    @Column(name = "online_status_type")
    private String onlineStatusType;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "video_duration")
    private String videoDuration;

    // ✅ week_id 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id")
    private LectureWeek week;

    // ✅ file_id 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    // ✅ lecture_id 외래키 (빠져있던 부분)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private LectureInfo lecture;



}
