# README

## 整体情况

使用了Featuretools作为数据的特征的提取工具，进行了数据标准化、异常值处理、缺失值处理。使用了XGboost和随机森林两种模型进行模型的训练。

### 所有数据链接

https://box.nju.edu.cn/d/c425cb925a824a488a77/

## 分工情况

| 人员                | 完成任务                                                     |
| ------------------- | ------------------------------------------------------------ |
| 201250203（张若皓） | 数据收集，数据预处理，特征工程，特征选择，模型选择，模型训练，报告整理，任务分配 |
| 201250209（李泽雨） | 数据收集，模型评估，模型应用                                 |
| 201250210（余灏沣） | 数据可视化，模型评估，模型应用                               |
| 201250190（张嵩）   | 数据收集，模型评估，模型应用                                 |
| 201850075（毛杰庄） | 数据可视化，模型评估，模型应用                               |

## 模型效果

### 客户星级 

| 模型         | 准确率             | 精确率             | 召回率             | F1分数             | Cohen's Kappa系数           |
| ------------ | ------------------ | ------------------ | ------------------ | ------------------ | --------------------------- |
| 随机森林模型 | 0.9738573071604875 | 0.9740772053204735 | 0.9738573071604875 | 0.973903174880576  | 0.9582329939228935          |
| XGBoost模型  | 0.7379495272495462 | 0.9202710328280544 | 0.5432862868389917 | 0.5654842522505414 | 0.8731477277261196 信用等级 |

### 信用等级

| 模型         | 准确率             | 精确率             | 召回率             | F1分数             | Cohen's Kappa系数  |
| ------------ | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ |
| 随机森林模型 | 0.9633147603033224 | 0.957026256269053  | 0.8455545355692532 | 0.8913416340143352 | 0.9203203653612596 |
| XGBoost模型  | 0.8522306355918314 | 0.6773776797965199 | 0.5835956243077896 | 0.6156770921619316 | 0.7289625204956423 |

## 可视化展示

（仅展示一张图）

![image-20230516141545757](https://typora-tes.oss-cn-shanghai.aliyuncs.com/picgo/image-20230516141545757.png)
