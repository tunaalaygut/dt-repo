package com.alaygut.prototype.domain;

/**
 * Toplantı taleplerinin durumları
 * ONAY_BEKLIYOR talep oluşturulmuş ve supervisor onayı bekliyor
 * ONAYLANDI talep supervisor/başka kullanıcı tarafından onaylandı. Şu an toplantı canlı.
 * REDDEDILDI talep supervisor/başka kullanıcı tarafından reddedildi.
 * IPTAL_EDILDI talep, oluşturan kullanıcı tarafından iptal edildi.
 * KULLANICI_ONAYI_BEKLIYOR başka bir kullanıcının canlı toplantısına talep yapıldı. Kullanıcının onayı bekleniyor.
 * TRANSFER_EDILDI kullanıcı kendi canlı toplantısını başka bir kullanıcıya verdi.
 */

public enum MeetingState {
    ONAY_BEKLIYOR,
    ONAYLANDI,
    REDDEDILDI,
    IPTAL_EDILDI,
    KULLANICI_ONAYI_BEKLIYOR,
    TRANSFER_EDILDI
}
