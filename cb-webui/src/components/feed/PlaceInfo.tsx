import { MapPin } from "lucide-react";

interface PlaceInfoProps {
  foodName: string;
  placeName: string;
  price: number;
}

export default function PlaceInfo({ foodName, placeName, price }: PlaceInfoProps) {
  // Format price helper
  const formatPrice = (val: number) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    }).format(val);
  };

  return (
    <div className="flex flex-col gap-1.5 w-full">
      {/* Food Name & Price */}
      <div className="flex items-start justify-between gap-3">
        <h2 className="text-[17px] sm:text-[19px] font-extrabold text-theme-text leading-snug transition-colors truncate pr-1">
          {foodName}
        </h2>
        <span className="text-[15px] sm:text-[17px] font-extrabold text-theme-accent shrink-0 transition-colors">
          {formatPrice(price)}
        </span>
      </div>

      {/* Place Details */}
      <div className="flex items-center gap-1 text-[12px] sm:text-[13px] text-theme-subtext font-semibold transition-colors">
        <MapPin className="w-[14px] h-[14px] shrink-0 text-theme-accent" />
        <span className="truncate">{placeName}</span>
      </div>
    </div>
  );
}
