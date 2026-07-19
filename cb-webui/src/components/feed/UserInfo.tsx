import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import { Check } from "lucide-react";

dayjs.extend(relativeTime);

interface UserInfoProps {
  fullName: string;
  userName: string;
  profileImageUrl: string | null;
  verified: boolean;
  createdAt: string;
}

export default function UserInfo({
  fullName,
  userName,
  profileImageUrl,
  verified,
  createdAt,
}: UserInfoProps) {
  // Format relative time: e.g. "2 mins ago", "5 hours ago", "Yesterday"
  const getRelativeTime = (timeStr: string) => {
    try {
      const date = dayjs(timeStr);
      const now = dayjs();
      
      if (now.diff(date, "day") === 1) {
        return "Yesterday";
      }
      return date.fromNow();
    } catch {
      return "Some time ago";
    }
  };

  // Profile picture fallback (first letter of full name)
  const fallbackInitial = fullName ? fullName.charAt(0).toUpperCase() : "?";

  return (
    <div className="flex items-center gap-3">
      {/* Avatar Container */}
      <div className="relative w-10 h-10 rounded-full overflow-hidden bg-theme-input-bg border border-theme-border flex items-center justify-center shrink-0">
        {profileImageUrl ? (
          <img
            src={profileImageUrl}
            alt={fullName}
            className="w-full h-full object-cover"
            onError={(e) => {
              // Fallback if image fails to load
              e.currentTarget.style.display = "none";
              const parent = e.currentTarget.parentElement;
              if (parent) {
                const span = document.createElement("span");
                span.className = "text-[14px] font-bold text-theme-primary";
                span.innerText = fallbackInitial;
                parent.appendChild(span);
              }
            }}
          />
        ) : (
          <span className="text-[14px] font-bold text-theme-primary">{fallbackInitial}</span>
        )}
      </div>

      {/* User Details */}
      <div className="flex flex-col min-w-0">
        <div className="flex items-center gap-1.5">
          <span className="text-[14px] sm:text-[15px] font-bold text-theme-text truncate leading-tight">
            {fullName}
          </span>
          {verified && (
            <div className="w-[14px] h-[14px] bg-sky-500 rounded-full flex items-center justify-center shrink-0" aria-label="Verified user">
              <Check className="w-[9px] h-[9px] text-white stroke-[3px]" />
            </div>
          )}
        </div>
        <div className="flex items-center gap-1.5 text-[11px] sm:text-[12px] text-theme-subtext">
          <span className="truncate">@{userName}</span>
          <span className="w-1 h-1 rounded-full bg-theme-border" />
          <span className="shrink-0">{getRelativeTime(createdAt)}</span>
        </div>
      </div>
    </div>
  );
}
