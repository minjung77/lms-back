# INSERT INTO role (role_id, role_name) VALUES (1, '관리자');
# INSERT INTO role (role_id, role_name) VALUES (2, '교수');
# INSERT INTO role (role_id, role_name) VALUES (3, '학생');
#
# INSERT INTO file (file_id, file_org_id, uuid, file_name, file_path, file_size) VALUES
#                                                                                    (1, 1001, 'uuid-101', 'syllabus_algo.pdf', '/cdn/files/', 204800),
#                                                                                    (2, 1002, 'uuid-102', 'syllabus_os.pdf', '/cdn/files/', 195000),
#                                                                                    (3, 1003, 'uuid-103', 'syllabus_digital.pdf', '/cdn/files/', 150000),
#                                                                                    (4, 1004, 'uuid-104', 'syllabus_micro.pdf', '/cdn/files/', 170000),
#                                                                                    (5, 1005, 'uuid-105', 'syllabus_philo.pdf', '/cdn/files/', 180000),
#                                                                                    (6, 1006, 'uuid-106', 'syllabus_lit.pdf', '/cdn/files/', 160000),
#                                                                                    (7, 1007, 'uuid-107', 'syllabus_bizintro.pdf', '/cdn/files/', 200000),
#                                                                                    (8, 1008, 'uuid-108', 'syllabus_marketing.pdf', '/cdn/files/', 210000);
# INSERT INTO stdt_info (stdt_id, stdt_name, gen_cd, hp_no, addr_dtl, zip, addr, email, major, stdt_birth, entry_year, password, state_cd, role_id) VALUES
#                                                                                                                                                       (20250001, 'stdt_name_1', 10, '010-1111-1111', 'addr_1', '12345', 'Seoul', 'stdt1@example.com', '컴퓨터공학과', '1999/02/20', '2025', 'password_1', 10, 3),
#                                                                                                                                                       (20250002, 'stdt_name_2', 20, '010-2222-2222', 'addr_2', '54321', 'Busan', 'stdt2@example.com', '경영학과', '1998/05/12', '2025', 'password_2', 10, 3);
# INSERT INTO prof_info (prof_id, prof_name, gen_cd, hp_no, zip_code, addr, addr_dtl, email, password, role_id) VALUES
#                                                                                                                   (1, 'prof_name_1', 10, '010-3333-3333', '12345', 'Seoul', 'addr_1', 'prof1@example.com', 'profpassword_1', 2),
#
#
#
#
#
#
#
#
#                                                                                                                   (2, 'prof_name_2', 10, '010-4444-4444', '54321', 'Busan', 'addr_2', 'prof2@example.com', 'profpassword_2', 2);
# INSERT INTO lecture_info (lecture_id, class_year, semester_cd, course_type, department, subject_code, subject_name, subject_level, credit, max_capacity, timetable, start_date, end_date, min_capacity, grade_level, subject_plan, prof_id, file_id, evaluator_a, evaluator_b) VALUES
#                                                                                                                                                                                                                                                                                    (1001, '2025', 10, '전필', '컴퓨터공학과', 'CS101', '자료구조', '학부', '3', '30', '월 1-2교시', '2025-03-02', '2025-06-14', '10', '2학년', '기초자료구조 설명', 1, 1, 2, 3),
#                                                                                                                                                                                                                                                                                    (1002, '2025', 10, '전선', '컴퓨터공학과', 'CS102', '운영체제', '학부', '3', '40', '화 3-4교시', '2025-03-02', '2025-06-14', '12', '3학년', '운영체제 기초', 2, 2, 3, 4);
# INSERT INTO lecture_week (week_id, lecture_id) VALUES
#                                                    (1, 1001),
#                                                    (2, 1001),
#                                                    (3, 1002),
#                                                    (4, 1002);
#
# INSERT INTO lecture_content (
#     lecture_management_id,
#     youtube_video_id,
#     lecture_call_url,
#     online_status_type,
#     chapter_name,
#     order_name,
#     week_id,
#     video_duration,
#     file_id,
#     lecture_id
# ) VALUES
#       (10001, 'vid-abc123', 'https://youtube.com/watch?v=abc123', '온라인', '1장: 알고리즘 개요', '1차시', 1, '15:00', 1, 1001),
#       (10002, 'vid-def456', 'https://youtube.com/watch?v=def456', '온라인', '2장: 정렬 알고리즘', '2차시', 2, '18:45', 2, 1001);

#
# INSERT INTO register_class (lecture_id, apply_date, attendance_rate, attendance_score, assignment_score, percentile_score, grade_evaluation, gpa_score, stdt_id) VALUES
#                                                                                                                                                                      (1001, '2025-03-01', 95, 90, 85, 88, 'A+', '3.5', 20250001),
#                                                                                                                                                                      (1002, '2025-03-01', 85, 80, 75, 78, 'B', '3.0', 20250002);
# INSERT INTO register_cart (stdt_id, priority_order, lecture_id) VALUES
#                                                                     (20250001, 1, 1001),
#                                                                     (20250002, 2, 1002);
# INSERT INTO board_info (board_type, board_name, use_yn) VALUES
#                                                             ('자유게시판', '공지사항', 'Y'),
#                                                             ('질문게시판', '질문과 답변', 'Y');
#
# -- lecture_assignment 테이블에 과제 정보 추가
# INSERT INTO lecture_assignment (assignment_id, title, description, start_datetime, end_datetime, submission_count, file_id, lecture_id,weekid) VALUES
#                                                                                                                                             (1, '과제 1', '자료구조 기초 과제', '2025-03-10 10:00:00', '2025-03-15 23:59:59', 0, 1, 1001,1),
#                                                                                                                                             (2, '과제 2', '운영체제 기초 과제', '2025-03-12 10:00:00', '2025-03-17 23:59:59', 0, 2, 1002,1);
#
#
# INSERT INTO assignment_submit (assignment_submit_id, submission_type, submission_date, score, assignment_id, file_id, lecture_id) VALUES
#                                                                                                                                       (1, '온라인', '2025-04-01', 85, 1, 1, 1001),
#                                                                                                                                       (2, '오프라인', '2025-04-02', 90, 2, 2, 1002);
#
# INSERT INTO academic_record (course_year, credit_applied, credit_earned, grade_percentage, term_avg_score, term_rank_score, grade_avg_score, term_avg_grade, grade_eval_count, joint_major_credit, joint_minor_credit, retaken_credit_count, semester_cd, lecture_id) VALUES
#                                                                                                                                                                                                                                                                           ('2025', '3', '3', '95', '92', '1', '91', 'A+', '1', '0', '0', '0', 10, 1001),
#
#
#                                                                                                                                                                                                                                                                           ('2025', '3', '3', '85', '80', '3', '79', 'B', '1', '0', '0', '0', 10, 1002);
# INSERT INTO admin (admin_name, hp_no, addr, addr_dtl, email, password, role_id) VALUES
#                                                                                     ('관리자1', '010-0000-0000', '서울시 강남구', 'addr_1', 'admin1@example.com', 'adminpass1', 1),
#                                                                                     ('관리자2', '010-1111-1111', '서울시 송파구', 'addr_2', 'admin2@example.com', 'adminpass2', 1);
#
# INSERT INTO lecture_progress (watch_time_total, watch_state, last_watch_time, lecture_name, content_id, watch_limit, progress_rate, stdt_id, final_played_time, max_play_time, lecture_management_id) VALUES
#                                                                                                                                                                                                           ('30:00', '완료', '30:00', '자료구조', 'abc123', '3', 100.0, 20250001, 600, 600, 10001),
#                                                                                                                                                                                                           ('20:00', '진행중', '20:00', '운영체제', 'def456', '3', 80.0, 20250002, 400, 500, 10002);
# -- 첫 번째 게시글 삽입
# INSERT INTO post (post_id, parent_post_id, title, content, writer, created_at, is_secret, board_id, prev_post_id, file_id) VALUES
#     (1, 0, '첫 번째 게시글', '강의에 대한 의견', '학생1', '2025-04-01', 'N', 1, NULL, 1);
#
# -- 두 번째 게시글 삽입
# INSERT INTO post (post_id, parent_post_id, title, content, writer, created_at, is_secret, board_id, prev_post_id, file_id) VALUES
#     (2, 1, '두 번째 게시글', '질문이 있습니다.', '학생2', '2025-04-02', 'N', 2, 1, 2);
#
#
# -- 부모 댓글 (첫 번째 댓글) 삽입
# INSERT INTO comment (comment_id, content, writer, created_at, post_id, parent_comment_id) VALUES
#     (1, '이 강의는 정말 유익했습니다!', '학생1', '2025-04-01', 1, NULL);
#
# -- 대댓글 (두 번째 댓글) 삽입
# INSERT INTO comment (comment_id, content, writer, created_at, post_id, parent_comment_id) VALUES
#     (2, '과제가 좀 어려웠어요.', '학생2', '2025-04-02', 1, 1);  -- parent_comment_id = 1
#
#
#
#
# -- lecture_assignment 테이블에 과제 정보 추가
# INSERT INTO lecture_assignment (assignment_id, title, description, start_datetime, end_datetime, submission_count, file_id, lecture_id,weekid) VALUES
#                                                                                                                                             (3, '과제 3', '자료구조2 기초 과제', '2025-03-14 10:00:00', '2025-03-18 23:59:59', 0, 1, 1001,3),
#                                                                                                                                             (4, '과제 4', '운영체제2 기초 과제', '2025-03-15 10:00:00', '2025-03-19 23:59:59', 0, 2, 1002,4);