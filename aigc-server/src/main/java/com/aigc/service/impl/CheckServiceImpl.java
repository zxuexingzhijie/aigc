package com.aigc.service.impl;

import com.aigc.result.Result;
import com.aigc.service.CheckService;
import com.aigc.vo.respvo.AnalysisResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


/**
 * @author ：jiang
 * @date ：2024/4/22 15:51
 * @description ：AIGC检测接口实现类
 */
@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private RestTemplate restTemplate;
    private String url = "http://47.109.58.42:4010";

    /**
     * 文本检测
     * @param file 文本数据
     * @return
     */
    @Override
    public Result checkText(MultipartFile file,String value) throws IOException {
//        String data = "{\n" +
//                "        \"whole_ai_prob\": 48.103,\n" +
//                "        \"whole_real_prob\": 51.897,\n" +
//                "        \"details\": [\n" +
//                "            {\n" +
//                "                \"text_number\": 1,\n" +
//                "                \"text\": \"尊敬的评委老师们：\",\n" +
//                "                \"real_probability\": 99.136,\n" +
//                "                \"AI_probability\": 0.864\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 2,\n" +
//                "                \"text\": \"大家晚上好！\",\n" +
//                "                \"real_probability\": 99.757,\n" +
//                "                \"AI_probability\": 0.243\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 3,\n" +
//                "                \"text\": \"我是“明甄谛听”项目的负责人蒋青江，很荣幸能够在这里向大家汇报我们基于对抗式网络的AIGC检测系统的研究成果。\",\n" +
//                "                \"real_probability\": 99.91,\n" +
//                "                \"AI_probability\": 0.09\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 4,\n" +
//                "                \"text\": \"在当前这个信息爆炸的时代，AIGC技术的广泛应用为我们带来了便捷，但同时也带来了信息真伪难辨的挑战。因此，我们团队致力于研发一款高效、准确的AIGC检测系统，为信息安全保驾护航。\",\n" +
//                "                \"real_probability\": 45.665,\n" +
//                "                \"AI_probability\": 54.335\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 5,\n" +
//                "                \"text\": \"接下来我将从政策导向、行业现状、产品介绍、团队介绍以及前景规划方面讲解，向大家详细介绍。\",\n" +
//                "                \"real_probability\": 85.948,\n" +
//                "                \"AI_probability\": 14.052\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 6,\n" +
//                "                \"text\": \"首先是政策导向这一部分，在图片中我们可以看到国家政府对生成式人工智能管理的高度重视，出台了生成式人工智能服务的管理暂行办法，政策的出台说明了促进生成式人工智能健康发展和防范生成式人工智能服务风险是当前现实需要。\",\n" +
//                "                \"real_probability\": 3.276,\n" +
//                "                \"AI_probability\": 96.724\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 7,\n" +
//                "                \"text\": \"接下来是我们的第二部分行业现状，首先是生成式AI在艺术创作领域中的影响，在艺术创作领域中生成式AI可能会导致创意的丧失和引发知识产权的争议，在右图中我们可以看到一些知识产权纠纷的一些事例。然后是第二点生成式AI在信息领域的影响，生成式AI在信息领域可能导致用户隐私泄露，也可能通过大数据“杀熟”使部分群体受到算法歧视，由于生成式AI能够生成逼真的文本和媒体内容，就可能会产生谣言和信息误导。第三点，生成式AI在学术领域的影响，在学术领域可能会造成学术诚信问题，信息缺乏可信度、学术失业风险，右侧是一些使用AI的学术不端的问题事例。最后讲解一下AIGC检测平台的行业现状，在国际方面目前已存AI生成作品检测系统发展缓慢，其中基本只包含了文案检测功能，检测准确率低，目前市面上已有的检测技术包括有：知网学术文献检测系统（已添加AI文本检测功能）、小型检测网站（几乎为国外网站）等，对中文检测效果更差。在国内方面         国内目前存在的AIGC检测系统仅仅针对学术文本进行检测，缺乏其他如图像、音频、视频的检测系统，国内检测网站收费也普遍较高，不适用于中低端市场（大量高校作业论文检测与中学作业检测）。最后是我们项目的技术难点，主要有以下四点：1. AI生成作品与人类作品具有高度相似性，计算机难于区分。2. 生成式AI的发展极为迅速，这也使得检测技术需要有高度成长性。3. 传统文本查重与图像相似度检测等技术不适用于AI生成类作品的检测。4. 没有完善的管控手段。与系统的处理方案，导致如今AI技术越发难于管理\",\n" +
//                "                \"real_probability\": 85.748,\n" +
//                "                \"AI_probability\": 14.252\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 8,\n" +
//                "                \"text\": \"接下来进入产品介绍部分，首先是产品演示，右侧是AI生成的一段见解类文案，左侧展示的是“明甄谛听”对该文案的检测结果，目前对于单段文案，我们已经取得了不错的效果。\",\n" +
//                "                \"real_probability\": 99.905,\n" +
//                "                \"AI_probability\": 0.095\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 9,\n" +
//                "                \"text\": \"然后是技术介绍，项目采用两项重要技术，第一项技术是成长式模型训练技术\",\n" +
//                "                \"real_probability\": 69.883,\n" +
//                "                \"AI_probability\": 30.117\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 10,\n" +
//                "                \"text\": \"在项目开发过程中，我们采用了对抗式网络的先进技术，对抗式技术：以生成对抗网络模型为基础核心，该网络模型一般是一个生成模型与判别模型之间进行对抗与训练得出的，当生成模型的仿真能力超过了判别模型就可以得到了我们所知的AIGC模型。而我们的技术便是通过团队内部技术成员自行设计的训练算法与对抗逻辑，训练出一个可以应对生成模型的判别模型。目前得出的模型便是Beta版预测模型——“谛听DIS-1.0”。\",\n" +
//                "                \"real_probability\": 11.274,\n" +
//                "                \"AI_probability\": 88.726\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 11,\n" +
//                "                \"text\": \"总的来说就是AIGC采用生成模型，而我们自己训练出一个可以应对生成模型的判别模型。\",\n" +
//                "                \"real_probability\": 90.481,\n" +
//                "                \"AI_probability\": 9.519\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 12,\n" +
//                "                \"text\": \"第二项技术是隐私保护训练技术，联邦学习技术：联邦学习技术可以在客户端就进行训练与参数返回，不用大规模采集用户数据，极大的保护了用户的数据隐私。\",\n" +
//                "                \"real_probability\": 1.041,\n" +
//                "                \"AI_probability\": 98.959\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 13,\n" +
//                "                \"text\": \"接下来是我们产品的核心竞争力，主要有以下四点：第一点，具有强大的市场潜力，目前的国家政策与国内市场需求，对于AIGC的管控需要进一步加强。第二点，有足够的创新，具有区别于传统查重系统与目前国内AI检测系统，我们开发了图像检测与音视频检测。第三点，保障数据隐私，采用联邦学习技术的使用可以极大的保护用户数据安全，防止数据泄露。\",\n" +
//                "                \"real_probability\": 4.31,\n" +
//                "                \"AI_probability\": 95.69\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 14,\n" +
//                "                \"text\": \"最后一点，面向中低端技术市场，我们模型部署完成后的检测成本极低\",\n" +
//                "                \"real_probability\": 97.137,\n" +
//                "                \"AI_probability\": 2.863\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 15,\n" +
//                "                \"text\": \"最后是我们的目标客户，“明甄谛听”——基于联邦对抗式网络的AI参与度检测系统\",\n" +
//                "                \"real_probability\": 99.973,\n" +
//                "                \"AI_probability\": 0.027\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 16,\n" +
//                "                \"text\": \"我们的产品是一款可以对AI参与工作程度进行检测的多平台检测系统，可以在网页端、PC应用程序、移动端应用进行使用，类似使用案例有用于如哔哩哔哩、抖音、腾讯视频等音视频平台可以部署进行作品的初步审查，判断其AI参与制作的程度；也可以与各类学习软件，例如学习通、中国大学慕课等多类学习软件进行后台部署，便以其进行作业AI参与度检测；或是可以用于小型报刊、小型网络媒体新闻资讯审查等。产品的核心理念是为中低端文献以及日常创作等常见领域进行初步检测，其检测对象则为AI参与程度，为未来规范化管理生成式人工智能做出些许贡献。\",\n" +
//                "                \"real_probability\": 29.906,\n" +
//                "                \"AI_probability\": 70.094\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 17,\n" +
//                "                \"text\": \"下一部分我们介绍一下我们的团队，我们的团队始于三位对人工智能充满热情的成员。随着项目的不断发展，我们逐渐增加了新的队员，直至现在的团队。每个新成员都带来了不同领域的专业知识和技能，这使得我们的项目不断得到完善和扩展。\",\n" +
//                "                \"real_probability\": 0.013,\n" +
//                "                \"AI_probability\": 99.987\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 18,\n" +
//                "                \"text\": \"最后一个部分前景规划，目前的系统正处于开发阶段初期，暂时不具有大规模应用的能力，我们正在对模型进行完善，对算法技术进行更新，预期2024年中将具有完备的部署能力。\",\n" +
//                "                \"real_probability\": 78.597,\n" +
//                "                \"AI_probability\": 21.403\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 19,\n" +
//                "                \"text\": \"彼时，我们将寻找初始合作伙伴进行首次系统商用测试，然后进一步实现我们的后期规划。\\nAIGC作品的管控方案亟待完事，在此背景下，设计与开发一款检测AIGC的系统是未来AI规范使用的必行之路，我们愿为此展现出自己的一份力量。\",\n" +
//                "                \"real_probability\": 0.311,\n" +
//                "                \"AI_probability\": 99.689\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 20,\n" +
//                "                \"text\": \"紧接着是我们项目未来的目标，主要有以下四点，第一点资金投入技术研发，提升产品技术这个核心竞争力，第二点与小型企业达成试用协议帮助检测系统的进一步完善，第三点开拓更加广阔的市场，进一步提升产品综合素质，第四点产品占据更大市场，得到更大发展空间与资金。\",\n" +
//                "                \"real_probability\": 0.02,\n" +
//                "                \"AI_probability\": 99.98\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 21,\n" +
//                "                \"text\": \"然后是我们项目的销售模式，有以下五点，1.网页销售模式2.许可证销售模式3.基于订阅服务的销售模式4.定制化解决方案模式5.授权模式\",\n" +
//                "                \"real_probability\": 99.546,\n" +
//                "                \"AI_probability\": 0.454\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 22,\n" +
//                "                \"text\": \"最后我们的团队正致力于开发基于人工智能和大数据的技术，某种程度上也是未来人工智能技术革命的缩影。即使我们的项目可能会遭遇失败，也为实现未来全国的人工智能技术革命贡献自己的一份力量！\",\n" +
//                "                \"real_probability\": 77.174,\n" +
//                "                \"AI_probability\": 22.826\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 23,\n" +
//                "                \"text\": \"“明甄谛听“筑好AI时代的最后一道防线，以上就是我们项目的全部内容。\",\n" +
//                "                \"real_probability\": 99.916,\n" +
//                "                \"AI_probability\": 0.084\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 24,\n" +
//                "                \"text\": \"谢谢大家！\",\n" +
//                "                \"real_probability\": 99.505,\n" +
//                "                \"AI_probability\": 0.495\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 25,\n" +
//                "                \"text\": \"尊敬的评委、老师们：\",\n" +
//                "                \"real_probability\": 99.742,\n" +
//                "                \"AI_probability\": 0.258\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 26,\n" +
//                "                \"text\": \"大家晚上好！\",\n" +
//                "                \"real_probability\": 99.757,\n" +
//                "                \"AI_probability\": 0.243\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 27,\n" +
//                "                \"text\": \"我是“明甄谛听”项目的负责人蒋青江，很荣幸能够在这里向大家汇报我们基于对抗式网络的AIGC检测系统的研究成果。\",\n" +
//                "                \"real_probability\": 99.91,\n" +
//                "                \"AI_probability\": 0.09\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 28,\n" +
//                "                \"text\": \"在当前这个信息爆炸的时代，AIGC技术的广泛应用为我们带来了便捷，但同时也带来了信息真伪难辨的挑战。因此，我们团队致力于研发一款高效、准确的AIGC检测系统，为信息安全保驾护航。\",\n" +
//                "                \"real_probability\": 45.665,\n" +
//                "                \"AI_probability\": 54.335\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 29,\n" +
//                "                \"text\": \"接下来我将从政策导向、行业现状、产品介绍、团队介绍以及前景规划方面讲解，向大家详细介绍。\",\n" +
//                "                \"real_probability\": 85.948,\n" +
//                "                \"AI_probability\": 14.052\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 30,\n" +
//                "                \"text\": \"首先是政策导向这一部分。在国家政府高度重视生成式人工智能管理的背景下，我们参照生成式人工智能服务管理暂行办法，旨在促进生成式人工智能健康发展与风险防范。\",\n" +
//                "                \"real_probability\": 1.716,\n" +
//                "                \"AI_probability\": 98.284\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 31,\n" +
//                "                \"text\": \"接下来是我们的第二部分行业现状。生成式AI在艺术创作领域可能引发创意丧失和知识产权争议，在信息领域可能导致用户隐私泄露以及大数据“杀熟”问题。而目前国内外的AIGC检测系统仍存在技术不足、检测范围有限等问题。\",\n" +
//                "                \"real_probability\": 98.372,\n" +
//                "                \"AI_probability\": 1.628\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 32,\n" +
//                "                \"text\": \"接下来进入产品介绍部分。我们的AIGC检测系统利用对抗式网络技术，采用成长式模型训练和隐私保护训练技术，具备图像、音频、视频等多维度检测能力。我们的核心竞争力在于市场潜力、创新性、数据隐私保护和低成本检测。\",\n" +
//                "                \"real_probability\": 76.172,\n" +
//                "                \"AI_probability\": 23.828\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 33,\n" +
//                "                \"text\": \"接下来是团队介绍。我们团队由对人工智能充满热情的成员组成，具备多领域专业知识和技能，致力于不断完善和拓展我们的项目。\",\n" +
//                "                \"real_probability\": 1.891,\n" +
//                "                \"AI_probability\": 98.109\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 34,\n" +
//                "                \"text\": \"最后是前景规划。我们的系统目前处于开发初期，但预计在2024年中将具备完备的部署能力。我们将寻找合作伙伴进行商用测试，并不断投入技术研发、拓展市场和提升产品质量。\",\n" +
//                "                \"real_probability\": 6.126,\n" +
//                "                \"AI_probability\": 93.874\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 35,\n" +
//                "                \"text\": \"在销售模式上，我们将采用网页销售、许可证销售、订阅服务、定制化解决方案和授权模式等多种方式。\",\n" +
//                "                \"real_probability\": 98.347,\n" +
//                "                \"AI_probability\": 1.653\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 36,\n" +
//                "                \"text\": \"最后，我们团队将继续努力开发基于人工智能和大数据的技术，为人工智能技术革命贡献一份力量。\",\n" +
//                "                \"real_probability\": 4.51,\n" +
//                "                \"AI_probability\": 95.49\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"text_number\": 37,\n" +
//                "                \"text\": \"“明甄谛听”筑好AI时代的最后一道防线，谢谢大家！\",\n" +
//                "                \"real_probability\": 99.886,\n" +
//                "                \"AI_probability\": 0.114\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }";
        String urlText = url+"/text";
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 将文件转换为 FileSystemResource 以便上传

        // 举个例子，我们先将文件保存到服务器的一个临时目录
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File tempFile = new File(tempDirectory, file.getOriginalFilename());
        file.transferTo(tempFile);

        FileSystemResource fileSystemResource = new FileSystemResource(tempFile.getAbsolutePath());
//        // 将文件内容写入 FileSystemResource
//        file.transferTo(fileSystemResource.getFile());

        // 设置请求体，包括文件和其他参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileSystemResource);
        body.add("model",value);

        // 创建请求实体对象
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity(urlText, requestEntity, String.class);

        // 回收资源
        fileSystemResource.getFile().delete();
        tempFile.delete();

        String json = response.getBody();
//         创建 ObjectMapper 实例
        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(response.getBody());
        AnalysisResult analysisResult = mapper.readValue(json, AnalysisResult.class);
        return Result.success(analysisResult);
    }

    /**
     * 图片检测
     * @param file
     * @return
     */
    @Override
    public Result checkImage(MultipartFile file) throws IOException {
        String urlImage = url+"/image";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File tempFile = new File(tempDirectory, Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(tempFile);

        FileSystemResource fileSystemResource = new FileSystemResource(tempFile.getAbsolutePath());
        // 设置请求体，包括文件和其他参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileSystemResource);
        // 创建请求实体对象
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity(urlImage, requestEntity, String.class);
        // 回收资源
        fileSystemResource.getFile().delete();
        tempFile.delete();

        String json = response.getBody();
//         创建 ObjectMapper 实例
        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(response.getBody());
        HashMap<String,String> analysisResult = mapper.readValue(json, HashMap.class);
        return Result.success(analysisResult);
    }

//    public String uploadFile(String url, MultipartFile file) throws IOException {
//        // 创建 RestTemplate 实例
////        RestTemplate restTemplate = new RestTemplate();
//
//        // 设置请求头
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        // 将文件转换为 FileSystemResource 以便上传
//        FileSystemResource fileSystemResource = new FileSystemResource(file.getOriginalFilename());
//
//        // 将文件内容写入 FileSystemResource
//        file.transferTo(fileSystemResource.getFile());
//
//        // 设置请求体，包括文件和其他参数
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", fileSystemResource);
//
//        // 创建请求实体对象
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        // 发送 POST 请求
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        // 回收资源
//        fileSystemResource.getFile().delete();
//
//        return response.getBody();
//    }
}
