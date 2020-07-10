package iacaasystem.admin.service;


import iacaasystem.admin.dao.QuestionnaireDao;
import iacaasystem.entity.QuestionnaireRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("qrservice")
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    QuestionnaireDao questionnaireDao;

    @Transactional
    @Override
    public boolean addQRs(List<QuestionnaireRecord> qrs) {
        int count = 0;
        for (QuestionnaireRecord qr: qrs) {
            count += questionnaireDao.insrtQR(qr);
        }
        return count == qrs.size();
    }
}
