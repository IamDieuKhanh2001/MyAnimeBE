package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.GiftcodeDTO;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.GiftCodeEntity;
import com.hcmute.myanime.model.SubscriptionPackageEntity;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.GiftcodeRepository;
import com.hcmute.myanime.repository.SubcriptionPackageRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftcodeService {
    @Autowired
    private GiftcodeRepository giftcodeRepository;
    @Autowired
    private SubcriptionPackageRepository subcriptionPackageRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private UserService userService;

    public boolean save(GiftcodeDTO giftcodeDTO, int packageId)
    {
        // Check package is exist
        Optional<SubscriptionPackageEntity> subscriptionPackageEntityOptional = subcriptionPackageRepository.findById(packageId);
        if(!subscriptionPackageEntityOptional.isPresent())
            return false;

        GiftCodeEntity giftCodeEntity = new GiftCodeEntity();
        giftCodeEntity.setRedemptionCode(giftcodeDTO.getRedemption_code());
        giftCodeEntity.setSubscriptionPackageById(subscriptionPackageEntityOptional.get());
        try {
            giftcodeRepository.save(giftCodeEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean destroy(int giftcodeId)
    {
        Optional<GiftCodeEntity> giftCodeEntityOptional = giftcodeRepository.findById(giftcodeId);
        if(!giftCodeEntityOptional.isPresent())
            return false;
        try {
            giftcodeRepository.delete(giftCodeEntityOptional.get());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean redeem(GiftcodeDTO giftcodeDTO)
    {
        // Check user was logged
        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
        Optional<UsersEntity> userLoggedIn = usersRepository.findByUsername(usernameLoggedIn);
        if(!userLoggedIn.isPresent()) {
            return false;
        }

        // check giftcode available
        List<GiftCodeEntity> giftCodeEntityList = giftcodeRepository.findByRedemptionCode(giftcodeDTO.getRedemption_code());
        if(giftCodeEntityList.isEmpty())
            return false;
        GiftCodeEntity giftCodeEntity = giftCodeEntityList.get(0);
        // Xu ly premium
        userService.createPremium(giftCodeEntity.getSubscriptionPackageById().getId());
        giftcodeRepository.delete(giftCodeEntity);

        return true;
    }
}
