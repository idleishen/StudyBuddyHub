-- 使用学友圈数据库
USE xueyouquan;

-- ========================
-- 1. 插入普通用户（不包含已存在的admin）
-- ========================
INSERT INTO `user` (`account`, `password`, `nickname`, `role`) VALUES
('zhangwei', '123456', '微风拂柳', 'USER'),
('liuna', '123456', '娜时花开', 'USER'),
('wangqiang', '123456', '强哥聊tech', 'USER'),
('zhaoyun', '123456', '云淡风轻', 'USER')
ON DUPLICATE KEY UPDATE 
    `nickname` = VALUES(`nickname`),
    `password` = VALUES(`password`);

-- ========================
-- 2. 插入帖子（user_id 通过 account 动态查询）
-- ========================
INSERT INTO `post` (`title`, `content`, `user_id`, `nickname`) VALUES
('刚看完《三体》电视剧，太震撼了！', '有没有一起追剧的？特效和剧情还原度都很高，聊聊你们的感受。', (SELECT id FROM `user` WHERE account='zhangwei'), '微风拂柳'),
('学习打卡｜坚持刷LeetCode第30天', '今天做了三道动态规划，感觉进步明显。有一起刷题的小伙伴吗？', (SELECT id FROM `user` WHERE account='wangqiang'), '强哥聊tech'),
('周末去看了樱花，拍了几张照片', '坐标武汉大学，人山人海但花很美。分享几张原图～', (SELECT id FROM `user` WHERE account='liuna'), '娜时花开'),
('求推荐Python数据分析入门书籍', '目前在看《利用Python进行数据分析》，还有没有其他经典教材？', (SELECT id FROM `user` WHERE account='zhaoyun'), '云淡风轻'),
('分享一个实用的Git命令小技巧', 'git reflog 真的能救回误删的commit，亲测有效！', (SELECT id FROM `user` WHERE account='wangqiang'), '强哥聊tech');

-- ========================
-- 3. 插入评论（user_id 和 post_id 动态查询）
-- ========================
INSERT INTO `comment` (`content`, `user_id`, `nickname`, `post_id`) VALUES
('同感！罗辑的选角太符合了', 
    (SELECT id FROM `user` WHERE account='liuna'), '娜时花开', 
    (SELECT id FROM `post` WHERE title LIKE '%三体%' LIMIT 1)),
('我还在看第一遍，刚看到古筝行动', 
    (SELECT id FROM `user` WHERE account='zhaoyun'), '云淡风轻', 
    (SELECT id FROM `post` WHERE title LIKE '%三体%' LIMIT 1)),
('太强了，我刷到easy就卡住了 😭', 
    (SELECT id FROM `user` WHERE account='zhangwei'), '微风拂柳', 
    (SELECT id FROM `post` WHERE title LIKE '%LeetCode%' LIMIT 1)),
('坚持就是胜利，一起加油！', 
    (SELECT id FROM `user` WHERE account='liuna'), '娜时花开', 
    (SELECT id FROM `post` WHERE title LIKE '%LeetCode%' LIMIT 1)),
('拍得真好，武汉的樱花季真的很美', 
    (SELECT id FROM `user` WHERE account='wangqiang'), '强哥聊tech', 
    (SELECT id FROM `post` WHERE title LIKE '%樱花%' LIMIT 1)),
('我也在学数据分析，推荐《Python数据科学手册》', 
    (SELECT id FROM `user` WHERE account='zhangwei'), '微风拂柳', 
    (SELECT id FROM `post` WHERE title LIKE '%Python%' LIMIT 1)),
('git reflog 神器！上次救了我一命', 
    (SELECT id FROM `user` WHERE account='zhaoyun'), '云淡风轻', 
    (SELECT id FROM `post` WHERE title LIKE '%git%' LIMIT 1)),
('感谢分享，已经收藏了', 
    (SELECT id FROM `user` WHERE account='liuna'), '娜时花开', 
    (SELECT id FROM `post` WHERE title LIKE '%git%' LIMIT 1));

-- ========================
-- 4. 更新帖子的评论计数（可选）
-- ========================
UPDATE `post` p 
SET `comment_count` = (
    SELECT COUNT(*) FROM `comment` c WHERE c.`post_id` = p.`id`
);

-- 验证数据（可选）
-- SELECT * FROM `user`;
-- SELECT * FROM `post`;
-- SELECT * FROM `comment`;